package com.example.weatherreport.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class WeatherDataDTO(
        @NotBlank(message = "Date must not be blank")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date must be in the format 'yyyy-MM-dd'")
        val date: String,
        val lat: Double,
        val lon: Double,
        @NotBlank(message = "City must not be blank")
        val city: String,
        @NotBlank(message = "State should not be blank")
        val state: String,
        @Size(min = 1, message = "The temperatures list must contain at least one value")
        val temperatures: List<Double>
)