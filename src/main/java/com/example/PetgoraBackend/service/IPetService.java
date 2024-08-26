package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.dto.PetDto;
import com.example.PetgoraBackend.dto.PetResponseDto;
import com.example.PetgoraBackend.dto.PetsDTO;
import com.example.PetgoraBackend.entity.Pet;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface IPetService {
    ResponseEntity<String> deletePet(Integer petId);
    PetDto addPet(PetDto petDto);

    PetDto updatePet(Integer petId, PetDto petDto);
    List<PetResponseDto> getAllPets();
    PetResponseDto getPetById(Integer petId);
    List<PetDto> getAllPetsByOwner(Integer Id);

    public List<PetsDTO> getCurrentUserPets() ;

    Pet uploadPetImage(Integer petId, byte[] image);

    PetsDTO updatePetImageUrl(Integer petId, String imageUrl);

    public PetResponseDto updateRequestCertifAndBirthDate(Integer id, String birthDateStr) ;


}