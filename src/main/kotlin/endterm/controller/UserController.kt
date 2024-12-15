package endterm.controller

import endterm.model.Dto.UserDto
import endterm.service.RestTemplateService
import endterm.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
    private val restTemplateService: RestTemplateService
) {

    @PostMapping("/login")
    fun loginPlatonus(@RequestBody user: UserDto): Any? {
        return restTemplateService.authenticate(user.login!!, user.password!!)
    }

}