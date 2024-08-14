package com.example.PetgoraBackend.mapper.petData;

import com.example.PetgoraBackend.dto.petData.HealthStatsDTO;
import com.example.PetgoraBackend.entity.petData.HealthStats;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HealthStatsMapper {

    HealthStatsMapper INSTANCE = Mappers.getMapper(HealthStatsMapper.class);

    @Mapping(source = "pet.id", target = "petId")
    HealthStatsDTO toDTO(HealthStats healthStats);

    @Mapping(source = "petId", target = "pet.id")
    HealthStats toEntity(HealthStatsDTO healthStatsDTO);
}
