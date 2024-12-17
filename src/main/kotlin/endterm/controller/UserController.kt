package endterm.controller

import endterm.model.Dto.HttpMessage
import endterm.model.User
import endterm.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
) {

    @CrossOrigin(origins = ["*"])
    @PostMapping("/login")
    fun loginPlatonus(@RequestBody user: User): HttpMessage? {
        return user.login?.let { user.password?.let { it1 -> userService.getAuthenticated(it, it1) } }
    }

    @CrossOrigin(origins = ["*"])
    @GetMapping("/getGrades")
    fun getGrades(): ResponseEntity<Any> {
        return userService.getGrades()
    }

    @CrossOrigin(origins = ["*"])
    @GetMapping("/getUserInfo")
    fun getUserInfo(@RequestBody user: User): ResponseEntity<Any>? {
        return user.login?.let { user.password?.let { it1 -> userService.getInfo(it, it1) } }
    }

}