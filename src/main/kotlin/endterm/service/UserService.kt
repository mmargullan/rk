package endterm.service

import endterm.model.Dto.UserDto
import endterm.model.User
import endterm.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class UserService (
    @Autowired private val userRepository: UserRepository,
    @Autowired private val restTemplateService: RestTemplateService
){

    fun getAuthenticated(login: String, password: String) {
        if (userRepository.findByLogin(login) == null) {
            val user = User().apply {
                this.login = login
                this.password = password
                this.personId = restTemplateService.authenticate(login, password)
            }
            if (user.personId != null) {
                userRepository.save(user)
            }
        }else{
            val personId = restTemplateService.authenticate(login, password)
            if (personId == null) {
                throw HttpClientErrorException(HttpStatus.BAD_REQUEST)
            }
        }

    }

    }

