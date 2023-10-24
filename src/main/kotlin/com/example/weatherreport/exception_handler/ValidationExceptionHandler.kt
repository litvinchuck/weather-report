package com.example.weatherreport.exception_handler

import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackages = ["com.example.weatherreport.controller"])
class ValidationExceptionHandler {

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(ex: ConstraintViolationException): ResponseEntity<String> {
        val violations = ex.constraintViolations
        val messages = violations.map { it.message }
        val errorMessage = messages.joinToString(", ")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage)
    }
}