package endterm.service

import endterm.model.Dto.UserDto
import endterm.model.PersonId
import endterm.model.User
import endterm.repository.PersonIdRepository
import endterm.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class UserService (
    @Autowired private val userRepository: UserRepository,
    @Autowired private val personIdRepository: PersonIdRepository,
    @Autowired private val restTemplateService: RestTemplateService
){

    fun getAuthenticated(login: String, password: String) {
        val user = User().apply {
            this.login = login
            this.password = password
        }

        val id = restTemplateService.authenticate(login, password)
        val personId = PersonId().apply {
            this.personID = id
            this.user = user
        }
        if (id != null) {
            userRepository.save(user)
            personIdRepository.save(personId)
        }else{
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad Request")
        }
    }

}