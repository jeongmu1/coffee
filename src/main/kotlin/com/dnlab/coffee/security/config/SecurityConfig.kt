package com.dnlab.coffee.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
class SecurityConfig {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .formLogin()
            .loginPage("/user/admin/login").loginProcessingUrl("/admin")
            .and()

            .logout().logoutSuccessUrl("/")
            .and()

            .authorizeHttpRequests {
                it
                    .requestMatchers("/vendor", "/vendor/**",
                        "/ingredient", "/ingredient/**",
                        "/supply", "/supply/**",
                        "/menu/admin", "/menu/admin/**").hasRole("ADMIN")
                    .requestMatchers("/", "/**").permitAll()
            }

        return httpSecurity.orBuild
    }

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()
}