package endterm.service

import endterm.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService (
    @Autowired private val userRepository: UserRepository
){



}