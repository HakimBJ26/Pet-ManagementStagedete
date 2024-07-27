package com.example.PetgoraBackend.mapper;

import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.entity.PetDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PetMapper {
    PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);

    PetDto toPetDto(Pet pet);
    Pet toPet(PetDto petDto);
}
