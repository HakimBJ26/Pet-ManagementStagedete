package com.example.PetgoraBackend.controller;

import com.example.PetgoraBackend.dto.PetDto;
import com.example.PetgoraBackend.dto.PetResponseDto;
import com.example.PetgoraBackend.service.IPetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
}
