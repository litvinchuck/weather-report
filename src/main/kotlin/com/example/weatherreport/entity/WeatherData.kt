package com.example.weatherreport.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class WeatherData (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var date: LocalDate,

    @Column(nullable = false)
    var lat: Double,

    @Column(nullable = false)
    var lon: Double,

    @Column(nullable = false)
    var city: String,

    @Column(nullable = false)
    var state: String,

    @ElementCollection
    @CollectionTable(name = "temperatures", joinColumns = [JoinColumn(name = "weather_data_id")])
    @Column(name = "temperature")
    var temperatures: List<Double>
)
