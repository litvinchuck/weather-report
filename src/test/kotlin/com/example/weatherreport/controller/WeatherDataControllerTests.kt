package com.example.weatherreport.controller

import com.example.weatherreport.dto.WeatherDataDTO
import com.example.weatherreport.exception.NotFoundException
import com.example.weatherreport.exception_handler.GlobalExceptionHandler
import com.example.weatherreport.service.WeatherDataService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDate
import java.time.format.DateTimeParseException


@ExtendWith(MockitoExtension::class)
class WeatherDataControllerTests {
    private lateinit var mockMvc: MockMvc

    @InjectMocks
    private lateinit var weatherDataController: WeatherDataController

    @Mock
    private lateinit var weatherDataService: WeatherDataService
    private lateinit var weatherDataDTO: WeatherDataDTO
    private val dateParam = "2023-10-25"
    private val cityParam = "New York"
    private val sortParam = "date"
    private val invalidParam = "aaaa"

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(weatherDataController)
            .setControllerAdvice(GlobalExceptionHandler::class.java)
            .build()

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
    fun testGetWeatherData_allParameters_returnsOk() {
        `when`(weatherDataService.getWeatherData(anyString(), anyString(), anyString()))
            .thenReturn(listOf(weatherDataDTO))

        mockMvc.perform(
            get("/api/weather")
                .param("date", dateParam)
                .param("city", cityParam)
                .param("sort", sortParam)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].city").value(weatherDataDTO.city))
            .andExpect(jsonPath("$[0].state").value(weatherDataDTO.state))

        verify(weatherDataService, times(1)).getWeatherData(anyString(), anyString(), anyString())
    }

    @Test
    fun testGetWeatherData_OnlyDate_returnsOk() {
        `when`(weatherDataService.getWeatherData(anyString(), any(), any()))
            .thenReturn(listOf(weatherDataDTO))

        mockMvc.perform(
            get("/api/weather")
                .param("date", dateParam)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].city").value(weatherDataDTO.city))
            .andExpect(jsonPath("$[0].state").value(weatherDataDTO.state))

        verify(weatherDataService, times(1)).getWeatherData(anyString(), any(), any())
    }

    @Test
    fun testGetWeatherData_onlyCity_returnsOk() {
        `when`(weatherDataService.getWeatherData(any(), anyString(), any()))
            .thenReturn(listOf(weatherDataDTO))

        mockMvc.perform(
            get("/api/weather")
                .param("city", cityParam)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].city").value(weatherDataDTO.city))
            .andExpect(jsonPath("$[0].state").value(weatherDataDTO.state))

        verify(weatherDataService, times(1)).getWeatherData(any(), anyString(), any())
    }

    @Test
    fun testGetWeatherData_onlySort_returnsOk() {
        `when`(weatherDataService.getWeatherData(any(), any(), anyString()))
            .thenReturn(listOf(weatherDataDTO))

        mockMvc.perform(
            get("/api/weather")
                .param("sort", sortParam)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].city").value(weatherDataDTO.city))
            .andExpect(jsonPath("$[0].state").value(weatherDataDTO.state))

        verify(weatherDataService, times(1)).getWeatherData(any(), any(), anyString())
    }

    @Test
    fun testGetWeatherData_invalidParameters_returnsBadRequest() {
        `when`(weatherDataService.getWeatherData(anyString(), anyString(), anyString()))
            .thenThrow(DateTimeParseException::class.java)

        mockMvc.perform(
            get("/api/weather")
                .param("date", invalidParam)
                .param("city", cityParam)
                .param("sort", sortParam)
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun testGetWeatherDataById_dataExists_returnsOk() {
        val id = 1
        `when`(weatherDataService.getWeatherDataById(anyLong()))
            .thenReturn(weatherDataDTO)

        mockMvc.perform(
            get("/api/weather/${id}"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.city").value(weatherDataDTO.city))
                .andExpect(jsonPath("$.state").value(weatherDataDTO.state))

        verify(weatherDataService, times(1)).getWeatherDataById(anyLong())
    }

    @Test
    fun testGetWeatherDataById_dataNotExists_returnsNotFound() {
        val id = 1
        `when`(weatherDataService.getWeatherDataById(anyLong()))
            .thenThrow(NotFoundException::class.java)

        mockMvc.perform(
            get("/api/weather/${id}"))
        .andExpect(status().isNotFound)

        verify(weatherDataService, times(1)).getWeatherDataById(anyLong())
    }

    @Test
    fun testCreateWeatherData_validData_returnsCreated() {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        val json = objectMapper.writeValueAsString(weatherDataDTO)

        mockMvc.perform(
            post("/api/weather")
                .content(json)
                .contentType("application/json")
        ).andExpect(status().isCreated)
    }

    @Test
    fun testCreateWeatherData_invalidData_returnsBadRequest() {
        val json = "{}"

        mockMvc.perform(
            post("/api/weather")
                .content(json)
                .contentType("application/json")
        ).andExpect(status().isBadRequest)
    }
}