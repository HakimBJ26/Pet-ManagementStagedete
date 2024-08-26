package com.example.PetgoraBackend.mapper.petData;


import com.example.PetgoraBackend.dto.petData.SleepPatternDTO;
import com.example.PetgoraBackend.entity.petData.SleepPattern;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SleepPatternMapper {

    @Mapping(source = "pet.id", target = "petId")
    SleepPatternDTO toDto(SleepPattern sleepPattern);

    @Mapping(source = "petId", target = "pet.id")
    SleepPattern toEntity(SleepPatternDTO sleepPatternDTO);
}
