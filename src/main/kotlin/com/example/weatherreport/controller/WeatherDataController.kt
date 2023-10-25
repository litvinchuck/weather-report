package com.example.weatherreport.controller

import com.example.weatherreport.dto.WeatherDataDTO
import com.example.weatherreport.entity.WeatherData
import com.example.weatherreport.service.WeatherDataService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/weather")
class WeatherDataController(private val weatherDataService: WeatherDataService) {

    @GetMapping
    fun getWeatherData(): List<WeatherDataDTO> {
        return weatherDataService.getWeatherData()
    }

    @GetMapping("/{id}")
    fun getWeatherDataById(@PathVariable id: Long): WeatherDataDTO {
        return weatherDataService.getWeatherDataById(id)
    }

    @PostMapping
    fun createWeatherData(@RequestBody @Valid weatherDataDTO: WeatherDataDTO): ResponseEntity<WeatherData> {
        val weatherData = weatherDataService.createWeatherData(weatherDataDTO)
        return ResponseEntity.status(HttpStatus.CREATED).body(weatherData)
    }
}