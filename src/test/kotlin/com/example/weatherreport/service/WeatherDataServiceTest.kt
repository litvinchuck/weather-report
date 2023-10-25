package com.example.weatherreport.service

import com.example.weatherreport.dto.WeatherDataDTO
import com.example.weatherreport.entity.WeatherData
import com.example.weatherreport.exception.NotFoundException
import com.example.weatherreport.mapper.WeatherDataMapper
import com.example.weatherreport.repository.WeatherDataRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.Sort
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class WeatherDataServiceTest {
    @Mock
    private lateinit var weatherDataRepository: WeatherDataRepository
    @Mock
    private lateinit var weatherDataMapper: WeatherDataMapper
    @InjectMocks

    private lateinit var weatherDataService: WeatherDataService

    private lateinit var weatherDataDTO: WeatherDataDTO
    private lateinit var weatherDataEntity: WeatherData
    private val dateString = "2023-10-25"
    private val citiesString = "City1,City2"
    private val sort = "date"
    private val sortDesc = "-date"

    @BeforeEach
    fun setup() {
        weatherDataDTO = WeatherDataDTO(
            null,
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
        weatherDataEntity = WeatherData(
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
    }

    @Test
    fun testCreateWeatherData_everythingIsOkay_weatherDateSaved() {
        `when`(weatherDataMapper.toEntity(weatherDataDTO)).thenReturn(weatherDataEntity)
        `when`(weatherDataRepository.save(weatherDataEntity)).thenReturn(weatherDataEntity)
        `when`(weatherDataMapper.toDTO(weatherDataEntity)).thenReturn(weatherDataDTO)

        val result = weatherDataService.createWeatherData(weatherDataDTO)

        assertEquals(weatherDataDTO, result)
        verify(weatherDataRepository, times(1)).save(weatherDataEntity)
    }

    @Test
    fun testGetWeatherData_noExtraData_returnsCorrectData() {
        val expectedSorting = Sort.by(Sort.Order.asc("id"))

        `when`(weatherDataRepository.findAll(any(), eq(expectedSorting)))
            .thenReturn(listOf(weatherDataEntity))
        `when`(weatherDataMapper.toDTO(weatherDataEntity)).thenReturn(weatherDataDTO)

        val result = weatherDataService.getWeatherData(null, null, null)

        verify(weatherDataRepository, times(1)).findAll(any(), eq(expectedSorting))
        assertEquals(1, result.size)
        assertEquals(weatherDataDTO, result[0])
    }

    @Test
    fun testGetWeatherData_dateOnly_returnsCorrectData() {
        val expectedSorting = Sort.by(Sort.Order.asc("id"))

        `when`(weatherDataRepository.findAll(any(), eq(expectedSorting)))
            .thenReturn(listOf(weatherDataEntity))
        `when`(weatherDataMapper.toDTO(weatherDataEntity)).thenReturn(weatherDataDTO)

        val result = weatherDataService.getWeatherData(dateString, null, null)

        verify(weatherDataRepository, times(1)).findAll(any(), eq(expectedSorting))
        assertEquals(1, result.size)
        assertEquals(weatherDataDTO, result[0])
    }

    @Test
    fun testGetWeatherData_citiesOnly_returnsCorrectData() {
        val expectedSorting = Sort.by(Sort.Order.asc("id"))

        `when`(weatherDataRepository.findAll(any(), eq(expectedSorting)))
            .thenReturn(listOf(weatherDataEntity))
        `when`(weatherDataMapper.toDTO(weatherDataEntity)).thenReturn(weatherDataDTO)

        val result = weatherDataService.getWeatherData(null, citiesString, null)

        verify(weatherDataRepository, times(1)).findAll(any(), eq(expectedSorting))
        assertEquals(1, result.size)
        assertEquals(weatherDataDTO, result[0])
    }

    @Test
    fun testGetWeatherData_sortDateAscOnly_returnsCorrectData() {
        val expectedSorting = Sort.by(Sort.Order.asc("date"), Sort.Order.asc("id"))

        `when`(weatherDataRepository.findAll(any(), eq(expectedSorting)))
            .thenReturn(listOf(weatherDataEntity))
        `when`(weatherDataMapper.toDTO(weatherDataEntity)).thenReturn(weatherDataDTO)

        val result = weatherDataService.getWeatherData(null, null, sort)

        verify(weatherDataRepository, times(1)).findAll(any(), eq(expectedSorting))
        assertEquals(1, result.size)
        assertEquals(weatherDataDTO, result[0])
    }

    @Test
    fun testGetWeatherData_sortDateDescOnly_returnsCorrectData() {
        val expectedSorting = Sort.by(Sort.Order.desc("date"), Sort.Order.asc("id"))

        `when`(weatherDataRepository.findAll(any(), eq(expectedSorting)))
            .thenReturn(listOf(weatherDataEntity))
        `when`(weatherDataMapper.toDTO(weatherDataEntity)).thenReturn(weatherDataDTO)

        val result = weatherDataService.getWeatherData(dateString, citiesString, sortDesc)

        verify(weatherDataRepository, times(1)).findAll(any(), eq(expectedSorting))
        assertEquals(1, result.size)
        assertEquals(weatherDataDTO, result[0])
    }

    @Test
    fun testGetWeatherDataById_dataIsPresent_dataIsReturned() {
        val id = 1L

        `when`(weatherDataRepository.findById(id)).thenReturn(java.util.Optional.of(weatherDataEntity))
        `when`(weatherDataMapper.toDTO(weatherDataEntity)).thenReturn(weatherDataDTO)

        val result = weatherDataService.getWeatherDataById(id)

        assertEquals(weatherDataDTO, result)
    }

    @Test
    fun testGetWeatherDataById_dataIsNotPresent_notFoundExceptionIsThrown() {
        val id = 1L

        `when`(weatherDataRepository.findById(id)).thenReturn(java.util.Optional.empty())

        assertThrows<NotFoundException> { weatherDataService.getWeatherDataById(id) }
    }
}