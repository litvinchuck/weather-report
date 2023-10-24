package com.example.weatherreport.controller

import com.example.weatherreport.entity.WeatherData
import com.example.weatherreport.service.WeatherDataService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/weather")
class WeatherDataController(private val weatherDataService: WeatherDataService) {

    @PostMapping
    fun createWeatherData(@RequestBody weatherData: WeatherData): WeatherData {
        return weatherDataService.createWeatherData(weatherData) //TODO: test response
    }

    @GetMapping
    fun getWeatherData(): List<WeatherData> {
        return weatherDataService.getWeatherData()
    }

    @GetMapping("/{id}")
    fun getWeatherDataById(@PathVariable id: String): ResponseEntity<WeatherData> {
        val weatherData = weatherDataService.getWeatherDataById(id)
        return if (weatherData != null) {
            ResponseEntity.ok(weatherData)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}