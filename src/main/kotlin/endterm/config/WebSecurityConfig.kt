package endterm.config

import endterm.service.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
@Configuration
class WebSecurityConfig(
    private val customUserDetailsService: CustomUserDetailsService
) : WebSecurityConfigurerAdapter() {

    override fun configure(httpSecurity: HttpSecurity){
        httpSecurity.csrf().disable()
            .authorizeRequests()
            .antMatchers("/user/register").permitAll()
            .and()
            .formLogin()
            .and()
            .httpBasic()
            .and()
    }

    override fun userDetailsService(): UserDetailsService {
        return customUserDetailsService
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}