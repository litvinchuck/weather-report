package com.example.weatherreport.service

import com.example.weatherreport.dto.WeatherDataDTO
import com.example.weatherreport.exception.NotFoundException
import com.example.weatherreport.mapper.WeatherDataMapper
import com.example.weatherreport.repository.WeatherDataRepository
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class WeatherDataService(
        private val weatherDataRepository: WeatherDataRepository,
        private val weatherDataMapper: WeatherDataMapper
) {

    fun createWeatherData(weatherDataDTO: WeatherDataDTO): WeatherDataDTO {
        val entity = weatherDataMapper.toEntity(weatherDataDTO)
        entity.id = null
        val savedEntity = weatherDataRepository.save(entity)
        return weatherDataMapper.toDTO(savedEntity)
    }

    fun getWeatherData(): List<WeatherDataDTO> {
        return weatherDataRepository.findAll()
                .stream()
                .map { weatherDataMapper.toDTO(it) }
                .collect(Collectors.toList())
    }

    fun getWeatherDataById(id: Long): WeatherDataDTO {
        val weatherData = weatherDataRepository.findById(id).orElseThrow {
            NotFoundException("Weather data for id '$id' not found")
        }
        return weatherDataMapper.toDTO(weatherData)
    }
}