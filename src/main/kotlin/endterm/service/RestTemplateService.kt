package endterm.service

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
class RestTemplateService(
    @Value("\${platonus.login.url}") val loginUrl: String,
    @Value("\${platonus.personID.url}") val personIDurl: String,
    @Value("\${platonus.grades.url}") val gradesUrl: String
) {

    val logger = LoggerFactory.getLogger(RestTemplateService::class.java)

    fun getPersonId(token: String): Long? {

        return try {
            val restTemplate = RestTemplate()
            val headers = HttpHeaders().apply {
                contentType = MediaType.APPLICATION_JSON
                set("Token", token)
            }

            val request = HttpEntity("{}",headers)

            val response = restTemplate.exchange(personIDurl, HttpMethod.GET, request, String::class.java)
            val jsonResponseID = Gson().fromJson(response.body, JsonObject::class.java)
            val personID = jsonResponseID["personID"]?.asLong

            personID

        }catch (e: Exception) {
            logger.error(e.message, e)
            null
        }

    }

    var cookie: String? = null

    fun getToken(login: String, password: String): String? {
        try {
            val restTemplate = RestTemplate()
            val headers = HttpHeaders().apply {
                contentType = MediaType.APPLICATION_JSON
            }

            val creds = LoginRequest(login, password)
            val gson = Gson()
            val credentials = gson.toJson(creds)
            val request = HttpEntity(credentials, headers)

            val response = restTemplate.exchange(loginUrl, HttpMethod.POST, request, String::class.java)
            val jsonResponse = Gson().fromJson(response.body, JsonObject::class.java)

            cookie = response.headers[HttpHeaders.SET_COOKIE]?.first()
            println(cookie)

            val token = jsonResponse["auth_token"]?.asString
            return token

        }catch (e: Exception) {
            logger.error(e.message, e)
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "Error while getting token")
        }
    }

    fun getGrades(personID: Long): ResponseEntity<String> {
        try {
            val restTemplate = RestTemplate()
            val headers = HttpHeaders().apply {
                contentType = MediaType.APPLICATION_JSON
                set("Cookie", cookie)
            }
            val request = HttpEntity("{}", headers)
            val response = restTemplate.exchange(gradesUrl, HttpMethod.GET, request, String::class.java)
            val jsonResponse = Gson().fromJson(response.body, JsonArray::class.java)
            print(jsonResponse)
            return ResponseEntity.ok(response.body)
        }catch (e: Exception) {
            logger.error(e.message, e)
            throw HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while getting grades")
        }
    }


    data class LoginRequest(val login: String, val password: String)

}