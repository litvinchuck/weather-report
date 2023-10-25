package com.example.weatherreport.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate


class WeatherDataDTO(
        var id: Long?,

        @field:NotNull(message = "'date' must not be empty")
        var date: LocalDate?,

        @field:NotNull(message = "'lat' must not be empty")
        var lat: Double?,

        @field:NotNull(message = "'lon' must not be empty")
        var lon: Double?,

        @field:NotEmpty(message = "'city' must not be empty")
        var city: String?,

        @field:NotEmpty(message = "'state' should not be empty")
        var state: String?,

        @field:Size(min = 1, message = "The temperatures list must contain at least one value")
        var temperatures: List<Double>?
)
