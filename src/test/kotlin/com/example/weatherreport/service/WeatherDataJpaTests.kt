package com.example.weatherreport.service

import com.example.weatherreport.entity.WeatherData
import com.example.weatherreport.repository.WeatherDataRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Sort
import java.time.LocalDate

@SpringBootTest
class WeatherDataJpaTests {
    @Autowired
    private lateinit var weatherDataRepository: WeatherDataRepository

    @Autowired
    private lateinit var weatherDataService: WeatherDataService
    private lateinit var weatherDataEntityOne: WeatherData
    private lateinit var weatherDataEntityTwo: WeatherData
    private val sorting: Sort = Sort.by(Sort.Order.asc("id"))

    @BeforeEach
    fun setup() {
        weatherDataEntityOne = WeatherData(
            1,
            LocalDate.now(),
            14.0,
            49.0,
            "Nashville",
            "Tennessee",
            listOf(
                17.3,
                16.8,
                16.4,
                16.0
            )
        )
        weatherDataEntityTwo = WeatherData(
            2,
            LocalDate.now().minusYears(1),
            14.0,
            49.0,
            "NashvilleTwo",
            "Tennessee",
            listOf(
                17.3,
                16.8,
                16.4,
                16.0
            )
        )
        weatherDataRepository.deleteAll()
        weatherDataRepository.save(weatherDataEntityOne)
        weatherDataRepository.save(weatherDataEntityTwo)
    }

    @Test
    fun testGenerateSpecification_noInput() {
        val specification = weatherDataService.generateSpecification(null, null)
        val result = weatherDataRepository.findAll(specification, sorting)

        assertEquals(2, result.size)
        assertEquals(weatherDataEntityOne.date, result[0].date)
        assertEquals(weatherDataEntityOne.city, result[0].city)
        assertEquals(weatherDataEntityTwo.date, result[1].date)
        assertEquals(weatherDataEntityTwo.city, result[1].city)
    }

    @Test
    fun testGenerateSpecification_onlyDate() {
        val dateString = weatherDataEntityOne.date.toString()

        val specification = weatherDataService.generateSpecification(dateString, null)
        val result = weatherDataRepository.findAll(specification, sorting)

        assertEquals(1, result.size)
        assertEquals(weatherDataEntityOne.date, result[0].date)
    }

    @Test
    fun testGenerateSpecification_onlyDateNotMatching() {
        val dateString = weatherDataEntityOne.date.plusDays(1).toString()

        val specification = weatherDataService.generateSpecification(dateString, null)
        val result = weatherDataRepository.findAll(specification, sorting)

        assertEquals(0, result.size)
    }

    @Test
    fun testGenerateSpecification_onlyOneCity() {
        val citiesString = weatherDataEntityOne.city.lowercase()

        val specification = weatherDataService.generateSpecification(null, citiesString)
        val result = weatherDataRepository.findAll(specification, sorting)

        assertEquals(1, result.size)
        assertEquals(weatherDataEntityOne.city, result[0].city)
    }

    @Test
    fun testGenerateSpecification_multipleCitiesOneMatches() {
        val citiesString = weatherDataEntityOne.city.lowercase() + ",london"

        val specification = weatherDataService.generateSpecification(null, citiesString)
        val result = weatherDataRepository.findAll(specification, sorting)

        assertEquals(1, result.size)
        assertEquals(weatherDataEntityOne.city, result[0].city)
    }

    @Test
    fun testGenerateSpecification_multipleCitiesTwoMatch() {
        val citiesString = weatherDataEntityOne.city.lowercase() + "," + weatherDataEntityTwo.city.lowercase()

        val specification = weatherDataService.generateSpecification(null, citiesString)
        val result = weatherDataRepository.findAll(specification, sorting)

        assertEquals(2, result.size)
        assertEquals(weatherDataEntityOne.city, result[0].city)
        assertEquals(weatherDataEntityTwo.city, result[1].city)
    }

    @Test
    fun testGenerateSpecification_multipleCitiesNoneMatch() {
        val citiesString = "london,paris"

        val specification = weatherDataService.generateSpecification(null, citiesString)
        val result = weatherDataRepository.findAll(specification, sorting)

        assertEquals(0, result.size)
    }
}