package com.example.PetgoraBackend.mapper;

import com.example.PetgoraBackend.dto.HealthPassportDto;
import com.example.PetgoraBackend.entity.HealthPassport;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HealthPassportMapper {
    HealthPassportMapper INSTANCE = Mappers.getMapper(HealthPassportMapper.class);
    HealthPassportDto toHealthPassportDto(HealthPassport healthPassport);
}
