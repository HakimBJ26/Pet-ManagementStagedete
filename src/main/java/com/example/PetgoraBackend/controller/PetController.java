package com.example.PetgoraBackend.controller;

import com.example.PetgoraBackend.dto.PetDto;
import com.example.PetgoraBackend.dto.PetResponseDto;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.service.IPetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
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
    public List<Pet> getCurrentUserPets() {
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

}

