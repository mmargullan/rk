package endterm.controller

import endterm.model.Dto.HttpMessage
import endterm.model.User
import endterm.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager
) {

    @PostMapping("/login")
    fun loginPlatonus(@RequestBody user: User): HttpMessage? {
        return user.login?.let { user.password?.let { it1 -> userService.getAuthenticated(it, it1) } }
    }

    @GetMapping("/getGrades")
    fun getGrades(): ResponseEntity<Any> {
        return userService.getGrades()
    }

    @GetMapping("/getUserInfo")
    fun getUserInfo(): ResponseEntity<Any>? {
        return userService.getInfo()
    }

}