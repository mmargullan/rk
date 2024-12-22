package endterm.service

import com.google.gson.Gson
import com.google.gson.JsonObject
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Component
class RestTemplateService(
    @Value("\${platonus.login.url}") val loginUrl: String,
    @Value("\${platonus.personID.url}") val personIDurl: String,
    @Value("\${platonus.grades.url}") val gradesUrl: String,
    @Value("\${platonus.userInfo.url}") val userInfoUrl: String
) {
    data class AuthResponse(
        val token: String?,
        val cookie: String?
    )

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

    fun getToken(login: String, password: String): AuthResponse {
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

            val cookie = response.headers[HttpHeaders.SET_COOKIE]?.first()
            val token = jsonResponse["auth_token"]?.asString

            return AuthResponse(token, cookie)

        }catch (e: Exception) {
            logger.error(e.message, e)
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "Error while getting token")
        }
    }

    fun getGrades(cookie: String): ResponseEntity<Any> {
        try {
            val restTemplate = RestTemplate()
            val headers = HttpHeaders().apply {
                contentType = MediaType.APPLICATION_JSON
                set("Cookie", cookie)
            }
            val request = HttpEntity("{}", headers)
            val response = restTemplate.exchange(gradesUrl, HttpMethod.GET, request, Any::class.java)
            return ResponseEntity.ok(response.body)
        }catch (e: Exception) {
            logger.error(e.message, e)
            throw HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while getting grades")
        }
    }

    fun getInformation(localtoken: String, cookie: String): ResponseEntity<Any> {
        try{
            val restTemplate = RestTemplate()
            val headers = HttpHeaders().apply {
                contentType = MediaType.APPLICATION_JSON
                set("Token", localtoken)
                set("Cookie", cookie)
            }
            val request = HttpEntity("{}", headers)
            val response = restTemplate.exchange(userInfoUrl, HttpMethod.POST, request, String::class.java)
            val userInfo: UserResponse = Gson().fromJson(response.body, UserResponse::class.java)
            val responseData = mutableMapOf<String, Any>()
            responseData["fullname"] = "${userInfo.student.lastnameEN} ${userInfo.student.firstnameEN}"
            responseData["gpa"] = userInfo.student.GPA
            responseData["specialization"] = userInfo.student.specializationNameEn
            responseData["course"] = userInfo.student.courseNumber
            return ResponseEntity.ok(responseData)
        }catch (e: Exception) {
            logger.error(e.message, e)
            throw HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while getting information")
        }
    }


    data class LoginRequest(val login: String, val password: String)

    data class Student(
        val lastnameEN: String,
        val firstnameEN: String,
        val GPA: Float,
        val specializationNameEn: String,
        val courseNumber: Long)
    data class UserResponse(val student: Student)

}