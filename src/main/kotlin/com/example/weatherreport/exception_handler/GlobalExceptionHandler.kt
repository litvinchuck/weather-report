package com.example.weatherreport.exception_handler

import com.example.weatherreport.exception.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.format.DateTimeParseException

@RestControllerAdvice(basePackages = ["com.example.weatherreport.controller"])
class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: Exception): ResponseEntity<ErrorResponse> {
        return handleException(ex, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(DateTimeParseException::class)
    fun handleDateTimeParseException(ex: Exception): ResponseEntity<ErrorResponse> {
        return handleException(ex, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorMessage = ex.bindingResult.fieldErrors
            .map { it.defaultMessage }
            .joinToString(separator = ", ")
        val error = ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST.value())
        return ResponseEntity.badRequest().body(error)
    }

    fun handleException(ex: Exception, status: HttpStatus): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(ex.message, status.value())
        return ResponseEntity(error, status)
    }
}

data class ErrorResponse(val message: String?, val status: Int)