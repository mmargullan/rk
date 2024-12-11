package endterm.service

import endterm.model.User
import endterm.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService (
    @Autowired private val userRepository: UserRepository,
    @Autowired private val passwordEncoder: PasswordEncoder
){

    fun registerUser(user: User): ResponseEntity<Any> {
        if (user.id?.let { userRepository.findById(it) } == null) {
            user.password = passwordEncoder.encode(user.password)
            user.role = "USER"
            userRepository.save(user)
            return ResponseEntity(HttpStatus.CREATED)
        }else{
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    fun getUsers(): List<User> {
        try {
            return userRepository.findAll()
        }
        catch (e: Exception) {
            e.printStackTrace()
            return listOf()
        }
    }

    fun deleteUser(username: String) {
        val user = userRepository.findByUsername(username)
        if (user != null) {
            userRepository.delete(user)
        }
    }

    fun getPaginated(page: Int, size: Int): Page<User> {
        val pageable = PageRequest.of(page, size)
        return userRepository.findAllPaginated(pageable)
    }

}