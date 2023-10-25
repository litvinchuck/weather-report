package com.example.weatherreport.controller

import com.example.weatherreport.dto.WeatherDataDTO
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate


@ExtendWith(MockitoExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class WeatherDataControllerSecurityTests {
    @Autowired
    private lateinit var mockMvc: MockMvc
    private lateinit var weatherDataDTO: WeatherDataDTO
    private val dateParam = "2023-10-25"

    @BeforeEach
    fun setup() {
        weatherDataDTO = WeatherDataDTO(
            null,
            LocalDate.parse(dateParam),
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
    }

    @Test
    @WithMockUser(roles = ["EDITOR"])
    fun accessingCreateWeatherData_withAuthAndCorrectRole_returnsCreated() {
        testAccessingCreateWeatherData(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    @WithMockUser
    fun accessingCreateWeatherData_withAuth_returnsForbidden() {
        testAccessingCreateWeatherData(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun accessingCreateWeatherData_withoutAuth_returnsForbidden() {
        testAccessingCreateWeatherData(MockMvcResultMatchers.status().isUnauthorized)
    }

    fun testAccessingCreateWeatherData(resultMatcher: ResultMatcher) {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        val json = objectMapper.writeValueAsString(weatherDataDTO)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/weather")
                .content(json)
                .contentType("application/json")
        ).andExpect(resultMatcher)
    }
}