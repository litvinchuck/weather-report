package com.example.weatherreport.service

import com.example.weatherreport.dto.WeatherDataDTO
import com.example.weatherreport.entity.WeatherData
import com.example.weatherreport.exception.NotFoundException
import com.example.weatherreport.mapper.WeatherDataMapper
import com.example.weatherreport.repository.WeatherDataRepository
import jakarta.persistence.criteria.Predicate
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.time.LocalDate


@Service
class WeatherDataService(
        private val weatherDataRepository: WeatherDataRepository,
        private val weatherDataMapper: WeatherDataMapper
) {

    fun createWeatherData(weatherDataDTO: WeatherDataDTO): WeatherDataDTO {
        val entity = weatherDataMapper.toEntity(weatherDataDTO).apply { id = null }
        val savedEntity = weatherDataRepository.save(entity)
        return weatherDataMapper.toDTO(savedEntity)
    }

    fun getWeatherData(dateString: String?, citiesString: String?, sort: String?): List<WeatherDataDTO> {
        val spec = generateSpecification(dateString, citiesString)

        val sorting = when (sort) {
            "date" -> Sort.by(Sort.Order.asc("date"), Sort.Order.asc("id"))
            "-date" -> Sort.by(Sort.Order.desc("date"), Sort.Order.asc("id"))
            else -> Sort.by(Sort.Order.asc("id"))
        }

        return weatherDataRepository.findAll(spec, sorting)
            .map { weatherDataMapper.toDTO(it) }
    }

    fun getWeatherDataById(id: Long): WeatherDataDTO {
        val weatherData = weatherDataRepository.findById(id).orElseThrow {
            NotFoundException("Weather data for id '$id' not found")
        }
        return weatherDataMapper.toDTO(weatherData)
    }

    fun generateSpecification(dateString: String?, citiesString: String?):Specification<WeatherData> {
        val date = dateString?.let { LocalDate.parse(it) }
        val cities = citiesString?.split(",") ?: emptyList()

        return Specification<WeatherData> { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()

            date?.let {
                predicates.add(criteriaBuilder.equal(root.get<LocalDate>("date"), it))
            }

            if (cities.isNotEmpty()) {
                val cityPredicates = cities.map { criteriaBuilder.equal(criteriaBuilder.lower(root.get("city")), it) }
                predicates.add(criteriaBuilder.or(*cityPredicates.toTypedArray()))
            }

            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }
}