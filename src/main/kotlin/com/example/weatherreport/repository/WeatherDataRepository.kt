package com.example.weatherreport.repository

import com.example.weatherreport.entity.WeatherData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface WeatherDataRepository : JpaRepository<WeatherData, Long>, JpaSpecificationExecutor<WeatherData>
