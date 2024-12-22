package endterm.service

import endterm.config.JwtTokenUtil
import endterm.model.Dto.HttpMessage
import endterm.model.Dto.UserDto
import endterm.model.User
import endterm.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class UserService(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val restTemplateService: RestTemplateService,
    private val jwtTokenUtil: JwtTokenUtil
){

    val token: String?
        get() {
            val authentication = SecurityContextHolder.getContext().authentication
            return (authentication.principal as UserDto).token
        }

    val cookie: String?
        get() {
            val authentication = SecurityContextHolder.getContext().authentication
            return (authentication.principal as UserDto).cookie
        }

    fun getAuthenticated(login: String, password: String): HttpMessage {

        val token = restTemplateService.getToken(login, password)
        val personId = token.token?.let { restTemplateService.getPersonId(it) }

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

            val jwtToken = token.cookie?.let { jwtTokenUtil.doGenerateToken(login, token.token, it) }

            return HttpMessage().apply {
                this.message = "Successfully logged in!"
                this.status = "Success"
                this.token = jwtToken
            }
        }

    }

    fun getGrades(): ResponseEntity<Any> {
        return restTemplateService.getGrades(cookie!!)
    }

    fun getInfo(): ResponseEntity<Any> {
        val response = restTemplateService.getInformation(token!!, cookie!!)
        return response
    }

}

