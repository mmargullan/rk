package endterm.controller

import com.google.gson.JsonArray
import endterm.model.Dto.HttpMessage
import endterm.model.User
import endterm.service.RestTemplateService
import endterm.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
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

    val logger = LoggerFactory.getLogger(UserController::class.java)

    @PostMapping("/login")
    fun loginPlatonus(@RequestBody user: User): HttpMessage? {
        return user.login?.let { user.password?.let { it1 -> userService.getAuthenticated(it, it1) } }
    }

    @GetMapping("/getGrades")
    fun getGrades(): ResponseEntity<Any> {
        return userService.getGrades()
    }

}