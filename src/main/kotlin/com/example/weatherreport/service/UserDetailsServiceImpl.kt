package com.example.weatherreport.service

import com.example.weatherreport.dto.UserDTO
import com.example.weatherreport.entity.Role
import com.example.weatherreport.entity.User
import com.example.weatherreport.exception.NotFoundException
import com.example.weatherreport.exception.UserAlreadyExistsException
import com.example.weatherreport.mapper.UserMapper
import com.example.weatherreport.repository.UserDetailsRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl (
    private val userRepository: UserDetailsRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userMapper: UserMapper
) : UserDetailsService {

    private val logger: Logger = LoggerFactory.getLogger(UserDetailsServiceImpl::class.java)

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByEmail(username.lowercase())
            .orElseThrow {
                logger.info("user for username $username not found")
                NotFoundException("Not found: $username")
            }
    }

    fun signUp(userRequest: UserDTO): UserDTO {
        if (userRepository.existsByEmail(userRequest.email.lowercase())) {
            logger.info("user for username {} already exists", userRequest.email)
            throw UserAlreadyExistsException("user ${userRequest.email} already exists")
        }

        val user = User(email = "example@example.com", userPassword = passwordEncoder.encode(userRequest.password))
        user.name = userRequest.name
        user.lastName = userRequest.lastName
        user.grantAuthority(Role.ROLE_USER)
        userRepository.save(user)
        logger.info("user $user signed up")

        return userMapper.toDTO(user)
    }
}