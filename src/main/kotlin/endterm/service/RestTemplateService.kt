package endterm.service

import com.google.gson.Gson
import com.google.gson.JsonObject
import endterm.model.PersonId
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class RestTemplateService(
    @Value("\${platonus.login.url}") val loginUrl: String,
    @Value("\${platonus.personID.url}") val personIDurl: String,
) {

    val logger = LoggerFactory.getLogger(RestTemplateService::class.java)

    fun authenticate(login: String, password: String): Long? {

        return try {
            //Authenticate and get token
            val restTemplate = RestTemplate()
            val authheaders = HttpHeaders().apply {
                contentType = MediaType.APPLICATION_JSON
            }

            val creds = LoginRequest(login, password)
            val gson = Gson()
            val credentials = gson.toJson(creds)
            val authrequest = HttpEntity(credentials, authheaders)

            val authresponse = restTemplate.exchange(loginUrl, HttpMethod.POST, authrequest, String::class.java)
            val jsonResponse = Gson().fromJson(authresponse.body, JsonObject::class.java)

            val token = jsonResponse["auth_token"]?.asString

            // Get person ID
            val idheaders = HttpHeaders().apply {
                contentType = MediaType.APPLICATION_JSON
                set("Token", token)
            }

            val request = HttpEntity("{}",idheaders)

            val response = restTemplate.exchange(personIDurl, HttpMethod.GET, request, String::class.java)
            val jsonResponseID = Gson().fromJson(response.body, JsonObject::class.java)
            val personID = jsonResponseID["personID"]?.asLong

            personID

        }catch (e: Exception) {
            logger.error(e.message, e)
            null
        }

    }


    data class LoginRequest(val login: String, val password: String)

}