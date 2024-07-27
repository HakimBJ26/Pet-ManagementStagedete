package com.example.PetgoraBackend.service.implementations;

import com.example.PetgoraBackend.dto.PetDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPetService {

    ResponseEntity<String> deletePet(Long petId);

    PetDto addPet(PetDto petDto);


    PetDto updatePet(Integer petId, PetDto petDto);

    ResponseEntity<String> deletePet(Integer petId);

    List<PetDto> getAllPets();



    PetDto getPetById(Integer petId);




    List<PetDto> getPetsByOwnerEmail(String ownerEmail);

    List<PetDto> getAllPetsByOwner(Integer Id);
}
