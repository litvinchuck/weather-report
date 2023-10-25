package com.example.weatherreport.mapper

import com.example.weatherreport.dto.UserDTO
import com.example.weatherreport.entity.User
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserMapper {
    fun toDTO(entity: User): UserDTO
    fun toEntity(dto: UserDTO): User
}