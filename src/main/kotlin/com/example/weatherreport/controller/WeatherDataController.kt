package com.example.weatherreport.controller

import com.example.weatherreport.dto.WeatherDataDTO
import com.example.weatherreport.service.WeatherDataService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/weather")
class WeatherDataController(private val weatherDataService: WeatherDataService) {

    @GetMapping
    fun getWeatherData(
        @RequestParam(required = false) date: String?,
        @RequestParam(required = false) city: String?,
        @RequestParam(required = false) sort: String?
    ): List<WeatherDataDTO> {
        return weatherDataService.getWeatherData(date, city, sort)
    }

    @GetMapping("/{id}")
    fun getWeatherDataById(@PathVariable id: Long): WeatherDataDTO {
        return weatherDataService.getWeatherDataById(id)
    }

    @PostMapping
    fun createWeatherData(@RequestBody @Valid weatherDataDTO: WeatherDataDTO): ResponseEntity<WeatherDataDTO> {
        val weatherData = weatherDataService.createWeatherData(weatherDataDTO)
        return ResponseEntity.status(HttpStatus.CREATED).body(weatherData)
    }
}