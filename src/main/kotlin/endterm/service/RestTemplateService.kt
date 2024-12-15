package endterm.service

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class RestTemplateService(
    @Value("\${platonus.login.url}") val loginUrl: String
) {

    fun authenticate(login: String, password: String): Any? {
        val restTemplate = RestTemplate()
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            set("Cookie", "JSESSIONID=37D61AFD0883E03935A3280545A7B956; plt_auth_cookie=63992e40b1aab506a4932459659ca102; plt_lang=1; plt_sid=7743a6b6e9bfe0d801583f3bdbf9a1e1; sessionid=37D61AFD0883E03935A3280545A7B956")
        }

        val creds = LoginRequest(login, password)
        val gson = Gson()
        val credentials = gson.toJson(creds)
        val request = HttpEntity(credentials, headers)

        return restTemplate.exchange(loginUrl, HttpMethod.POST, request, String::class.java)
    }

    data class LoginRequest(val login: String, val password: String)

}