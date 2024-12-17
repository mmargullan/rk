package endterm.service

import com.google.gson.JsonArray
import endterm.model.Dto.HttpMessage
import endterm.model.User
import endterm.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class UserService (
    @Autowired private val userRepository: UserRepository,
    @Autowired private val restTemplateService: RestTemplateService
){

    fun getAuthenticated(login: String, password: String): HttpMessage {

        val token = restTemplateService.getToken(login, password)
        val personId = token?.let { restTemplateService.getPersonId(it) }

        if(personId == null){
            throw HttpClientErrorException(HttpStatus.FORBIDDEN, "Bad Credentials!")
        }else{
            val user = User().apply {
                this.login = login
                this.password = password
                this.personId = personId
            }

            if (user.login?.let { userRepository.findByLogin(it) } == null){
                userRepository.save(user)
            }

            return HttpMessage().apply {
                this.message = "Successfully logged in!"
                this.status = "Success"
            }
        }

    }

    fun getGrades(): ResponseEntity<Any> {
        return restTemplateService.getGrades()
    }

    fun getInfo(login: String, password: String): ResponseEntity<Any> {
        val token = restTemplateService.getToken(login, password)
        return restTemplateService.getInformation(token)
    }

}

