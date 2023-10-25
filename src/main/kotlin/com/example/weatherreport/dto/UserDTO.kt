package com.example.weatherreport.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class UserDTO(
    @field:NotBlank(message = "name property should not be blank")
    val name: String? = null,

    @field:NotBlank(message = "lastname property should not be blank")
    @field:JsonProperty("lastname")
    val lastName: String? = null,

    @field:NotBlank(message = "email property should not be blank")
    @field:Email(message = "email is not valid")
    val email: String,

    @field:NotEmpty(message = "password property must be present")
    @field:Size(min = 12)
    val password: String
)