package com.example.weatherreport.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
data class WeatherData (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val date: LocalDate,

    @Column(nullable = false)
    val lat: Double,

    @Column(nullable = false)
    val lon: Double,

    @Column(nullable = false)
    val city: String,

    @Column(nullable = false)
    val state: String,

    @ElementCollection
    @CollectionTable(name = "temperatures", joinColumns = [JoinColumn(name = "weather_data_id")])
    @Column(name = "temperature")
    val temperatures: List<Double>
)