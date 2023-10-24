package com.example.weatherreport.mapper

import com.example.weatherreport.dto.WeatherDataDTO
import com.example.weatherreport.entity.WeatherData
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface WeatherDataMapper {

    fun toDTO(entity: WeatherData): WeatherDataDTO

    fun toEntity(dto: WeatherDataDTO): WeatherData
}