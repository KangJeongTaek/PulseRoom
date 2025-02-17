package com.kjt.PulseRoom.config


import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.core.userdetails.User

@Configuration
class SecurityConfig(
    private val env:Environment
) {


    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/posts/**").authenticated()
                    .requestMatchers("/pulse-chat/**").permitAll()
                    .requestMatchers("/admin/**").authenticated()
                    .requestMatchers("/comment/**").authenticated()
                    .anyRequest().permitAll()
            }
            .httpBasic(Customizer.withDefaults())
            .formLogin { it.disable() }
            .cors {  }
            .csrf { it.ignoringRequestMatchers("/posts/**").ignoringRequestMatchers("/comment/**").ignoringRequestMatchers("/pulse-chat/**").disable() }

        return http.build()
    }


}