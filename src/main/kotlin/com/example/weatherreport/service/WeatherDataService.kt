package com.example.weatherreport.service

import com.example.weatherreport.entity.WeatherData
import com.example.weatherreport.repository.WeatherDataRepository
import org.springframework.stereotype.Service

@Service
class WeatherDataService(private val weatherDataRepository: WeatherDataRepository) {

    fun createWeatherData(weatherData: WeatherData): WeatherData {
        // Add any business logic or validation here if needed
        return weatherDataRepository.save(weatherData)
    }

    fun getWeatherData(): List<WeatherData> {
        return weatherDataRepository.findAll()
    }

    fun getWeatherDataById(id: String): WeatherData? {
        return weatherDataRepository.findById(id).orElse(null)
    }
}