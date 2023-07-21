package com.lynas.demospringoauth2resourceserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@SpringBootApplication
class DemoSpringOauth2ResourceServerApplication

fun main(args: Array<String>) {
	runApplication<DemoSpringOauth2ResourceServerApplication>(*args)
}

@RestController
class DemoController {

	@GetMapping("/hello")
	fun demo() = "demo"

	@GetMapping("/hi")
	fun hi() = "hi"
}

@Configuration
@EnableWebSecurity
class SecurityConfig {

	@Bean
	fun securityFilterChain(http: HttpSecurity) : SecurityFilterChain {
		return http
				.authorizeHttpRequests {
					it.requestMatchers(
							"/hello"
					).permitAll()
					it.anyRequest().authenticated()
				}
				.cors{
					it.disable()
				}
				.sessionManagement {
					it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				}
				.oauth2ResourceServer{rs->
					rs.jwt(Customizer.withDefaults())
				}
				.build()
	}
}