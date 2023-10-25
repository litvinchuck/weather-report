package com.example.weatherreport.controller

import com.example.weatherreport.dto.UserDTO
import com.example.weatherreport.service.UserDetailsServiceImpl
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController (private val userDetailsService: UserDetailsServiceImpl) {

    private val logger: Logger = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("signup")
    fun signUp(@RequestBody @Valid user: UserDTO): UserDTO {
        logger.info("POST api/auth/signup body={$user}")
        return userDetailsService.signUp(user)
    }
}