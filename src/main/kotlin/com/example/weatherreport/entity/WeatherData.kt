package com.example.weatherreport.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "weatherData")
class WeatherData (
    @Id
    val id: String? = null,
    val date: String,
    val lat: Double,
    val lon: Double,
    val city: String,
    val state: String,
    val temperatures: List<Double>
)