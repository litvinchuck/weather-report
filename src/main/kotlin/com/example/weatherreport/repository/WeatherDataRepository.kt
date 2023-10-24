package com.example.weatherreport.repository

import com.example.weatherreport.entity.WeatherData
import org.springframework.data.mongodb.repository.MongoRepository

interface WeatherDataRepository : MongoRepository<WeatherData, String> {
}