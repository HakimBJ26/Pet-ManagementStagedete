package com.example.PetgoraBackend.controller.petData;

import com.example.PetgoraBackend.dto.petData.PetGoalDto;
import com.example.PetgoraBackend.entity.petData.PetGoal;
import com.example.PetgoraBackend.mapper.petData.PetGoalMapper;
import com.example.PetgoraBackend.service.petData.PetGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/petgoals")
public class PetGoalController {

    @Autowired
    private PetGoalService petGoalService;

    @Autowired
    private PetGoalMapper petGoalMapper; // Inject the mapper

    @PostMapping
    public ResponseEntity<PetGoalDto> createPetGoal(@RequestBody PetGoalDto petGoalDTO) {
        PetGoal petGoal = petGoalMapper.toEntity(petGoalDTO);
        PetGoal createdPetGoal = petGoalService.createPetGoal(petGoal);
        PetGoalDto createdPetGoalDTO = petGoalMapper.toDTO(createdPetGoal);
        return new ResponseEntity<>(createdPetGoalDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetGoalDto> getPetGoalById(@PathVariable Integer id) {
        Optional<PetGoal> petGoal = petGoalService.getPetGoalById(id);
        return petGoal.map(value -> ResponseEntity.ok(petGoalMapper.toDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PetGoalDto>> getAllPetGoals() {
        List<PetGoal> petGoals = petGoalService.getAllPetGoals();
        List<PetGoalDto> petGoalDTOs = petGoals.stream()
                .map(petGoalMapper::toDTO)
                .toList();
        return ResponseEntity.ok(petGoalDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetGoalDto> updatePetGoal(@PathVariable Integer id, @RequestBody PetGoalDto petGoalDTO) {
        PetGoal petGoal = petGoalMapper.toEntity(petGoalDTO);
        PetGoal updatedPetGoal = petGoalService.updatePetGoal(id, petGoal);
        PetGoalDto updatedPetGoalDTO = petGoalMapper.toDTO(updatedPetGoal);
        return ResponseEntity.ok(updatedPetGoalDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetGoal(@PathVariable Integer id) {
        petGoalService.deletePetGoal(id);
        return ResponseEntity.noContent().build();
    }
}
