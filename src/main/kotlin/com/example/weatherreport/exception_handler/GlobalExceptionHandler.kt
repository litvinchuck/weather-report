package com.example.weatherreport.exception_handler

import com.example.weatherreport.exception.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackages = ["com.example.weatherreport.controller"])
class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(ex.message, HttpStatus.NOT_FOUND.value())
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value())
        return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

data class ErrorResponse(val message: String?, val status: Int)