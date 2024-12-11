package endterm.service

import endterm.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.User as SecurityUser

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException(username)

        val authorities = user.role?.split(",")?.map { role ->
            SimpleGrantedAuthority("ROLE_${role.trim().uppercase()}")
        } ?: listOf()

        return SecurityUser.withUserDetails(
            org.springframework.security.core.userdetails.User
                .withUsername(user.username)
                .password(user.password)
                .authorities(authorities)
                .build()
        ).build()
    }

}