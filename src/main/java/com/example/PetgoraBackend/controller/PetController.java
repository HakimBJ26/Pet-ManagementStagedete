package com.example.PetgoraBackend.controller;

import com.example.PetgoraBackend.dto.PetDto;
import com.example.PetgoraBackend.dto.PetResponseDto;
import com.example.PetgoraBackend.dto.PetsDTO;
import com.example.PetgoraBackend.dto.petData.PetCertifDto;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.service.IPetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pets")
public class PetController {
    @Autowired
    private final IPetService petService;

    @GetMapping
    public List<PetResponseDto> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping("/current")
    public List<PetsDTO> getCurrentUserPets() {
        return petService.getCurrentUserPets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDto> getPetById(@PathVariable Integer id) {
        PetResponseDto pet = petService.getPetById(id);
        if (pet != null) {
            return ResponseEntity.ok(pet);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public PetDto createPet(@RequestBody PetDto pet) {
        return petService.addPet(pet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePet(@PathVariable Integer id) {
        return petService.deletePet(id);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDto> getPetsByOwner(@PathVariable Integer ownerId) {
        return petService.getAllPetsByOwner(ownerId);
    }

    @PutMapping("/{id}")
    public PetDto updatePet(@PathVariable Integer id, @RequestBody PetDto pet) {
        return petService.updatePet(id, pet);
    }

    @PostMapping("/{id}/image")
    public Pet uploadPetImage(@PathVariable Integer id, @RequestParam("photo") MultipartFile file) {
        try {
            byte[] imageBytes = file.getBytes();
            return petService.uploadPetImage(id, imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload image");
        }
    }


    @PutMapping("/{petId}/image-url")
    public ResponseEntity<PetsDTO> updatePetImageUrl(
            @PathVariable Integer petId,
            @RequestParam String imageUrl) {
        PetsDTO updatedPet = petService.updatePetImageUrl(petId, imageUrl);
        return ResponseEntity.ok(updatedPet);
    }


    @PostMapping("/request-certif/{id}")
    public ResponseEntity<PetResponseDto> updateRequestCertifAndBirthDate(
            @PathVariable Integer id,
            @RequestParam("birthDate") String birthDate) {
        PetResponseDto updatedPet = petService.updateRequestCertifAndBirthDate(id, birthDate);
        return ResponseEntity.ok(updatedPet);
    }

    @GetMapping("/certif-requests")
    public List<PetCertifDto> getPetsWithRequestedCertifAndNoBlockchainCert() {
        return petService.getPetsWithRequestedCertifAndNoBlockchainCert();
    }
}