package endterm.service

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService: UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return User.builder()
            .username(username)
            .password("")
            .authorities("USER")
            .build()
    }

}