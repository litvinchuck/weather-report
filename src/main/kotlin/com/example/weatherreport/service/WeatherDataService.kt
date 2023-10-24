package com.example.weatherreport.service

import com.example.weatherreport.dto.WeatherDataDTO
import com.example.weatherreport.entity.WeatherData
import com.example.weatherreport.mapper.WeatherDataMapper
import com.example.weatherreport.repository.WeatherDataRepository
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class WeatherDataService(
        private val weatherDataRepository: WeatherDataRepository,
        private val weatherDataMapper: WeatherDataMapper
) {

    fun createWeatherData(weatherDataDTO: WeatherDataDTO): WeatherData {
        return weatherDataRepository.save(weatherDataMapper.toEntity(weatherDataDTO))
    }

    fun getWeatherData(): List<WeatherDataDTO> {
        return weatherDataRepository.findAll()
                .stream()
                .map { weatherDataMapper.toDTO(it) }
                .collect(Collectors.toList())
    }

    fun getWeatherDataById(id: String): WeatherDataDTO {
        return weatherDataMapper.toDTO(weatherDataRepository.findById(id).orElse(null))
    }
}