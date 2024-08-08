package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.dto.PetDto;
import com.example.PetgoraBackend.dto.PetResponseDto;
import com.example.PetgoraBackend.entity.Pet;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPetService {
    ResponseEntity<String> deletePet(Integer petId);
    PetDto addPet(PetDto petDto);

    PetDto updatePet(Integer petId, PetDto petDto);
    List<PetResponseDto> getAllPets();
    PetResponseDto getPetById(Integer petId);
    List<PetDto> getAllPetsByOwner(Integer Id);

    List<Pet> getCurrentUserPets();

    Pet uploadPetImage(Integer petId, byte[] image);

}

