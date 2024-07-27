package com.example.PetgoraBackend.controller;

import com.example.PetgoraBackend.entity.PetDto;
import com.example.PetgoraBackend.service.implementations.IPetService;
import com.example.PetgoraBackend.service.implementations.PetServiceImp;
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
    public List<PetDto> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDto> getPetById(@PathVariable Integer id) {
        PetDto pet = petService.getPetById(id);
        if (pet != null) {
            return ResponseEntity.ok(pet);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public PetDto createPet(@RequestBody PetDto pet, @RequestParam Integer ownerId) {
        return petService.addPet(pet, ownerId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }

    // Add more endpoints as needed
}
