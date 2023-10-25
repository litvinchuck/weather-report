package com.example.weatherreport.repository

import com.example.weatherreport.entity.WeatherData
import org.springframework.data.jpa.repository.JpaRepository

interface WeatherDataRepository : JpaRepository<WeatherData, Long>