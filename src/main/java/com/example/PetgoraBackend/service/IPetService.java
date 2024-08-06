package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.dto.PetDto;
import com.example.PetgoraBackend.dto.PetResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPetService {

    ResponseEntity<String> deletePet(Integer petId);

    PetDto addPet(PetDto petDto);


    PetDto updatePet(Integer petId, PetDto petDto);

    List<PetResponseDto> getAllPets();



    PetResponseDto getPetById(Integer petId);




    List<PetDto> getPetsByOwnerEmail(String ownerEmail);

    List<PetDto> getAllPetsByOwner(Integer Id);
}
