package endterm.controller

import endterm.model.User
import endterm.repository.UserRepository
import endterm.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.awt.print.Pageable

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/register")
    fun register(@RequestBody user: User): ResponseEntity<Any> {
        return userService.registerUser(user)
    }

    @GetMapping("/all")
    fun all(): List<User> {
        return userService.getUsers()
    }

    @DeleteMapping("/delete")
    fun delete(@RequestParam username: String) {
        userService.deleteUser(username)
    }

    @GetMapping("/getPaginated")
    fun getPaginated(page: Int, size: Int): Page<User> {
        return userService.getPaginated(page, size)
    }

}