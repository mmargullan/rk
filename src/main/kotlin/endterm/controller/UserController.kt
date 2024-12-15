package endterm.controller

import endterm.model.Dto.HttpMessage
import endterm.model.User
import endterm.service.RestTemplateService
import endterm.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val restTemplateService: RestTemplateService,
    private val userService: UserService,
) {

    @PostMapping("/login")
    fun loginPlatonus(@RequestBody user: User): HttpMessage? {
        return user.login?.let { user.password?.let { it1 -> userService.getAuthenticated(it, it1) } }
    }

    @GetMapping("/getGrades")
    fun getGrades(){

    }

}