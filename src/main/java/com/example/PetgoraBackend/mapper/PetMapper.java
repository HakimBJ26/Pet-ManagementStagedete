package com.example.PetgoraBackend.mapper;

import com.example.PetgoraBackend.dto.CurrentUserPetResponseDto;
import com.example.PetgoraBackend.dto.PetResponseDto;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.dto.PetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PetMapper {
    PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "imageUrl", target = "imageUrl")
    PetResponseDto toPetResponseDto(Pet pet);

    PetDto toPetDto(Pet pet);
    Pet toPet(PetDto petDto);
    CurrentUserPetResponseDto toCurrentUserPetResponseDto(Pet pet);
}
