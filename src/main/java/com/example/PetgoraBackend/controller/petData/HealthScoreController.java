package com.example.PetgoraBackend.controller.petData;

import com.example.PetgoraBackend.dto.petData.HealthScoreDto;
import com.example.PetgoraBackend.service.petData.HealthScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health-score")
public class HealthScoreController {

    @Autowired
    private HealthScoreService healthScoreService;

    @GetMapping("/pet/{petId}")
    public HealthScoreDto getHealthScoreByPetId(@PathVariable Integer petId) {
        return healthScoreService.getHealthScoreByPetId(petId);
    }

    @PostMapping
    public HealthScoreDto saveHealthScore(@RequestBody HealthScoreDto healthScoreDTO) {
        return healthScoreService.saveHealthScore(healthScoreDTO);
    }

    @PutMapping("/{id}")
    public HealthScoreDto updateHealthScore(@PathVariable Long id, @RequestBody HealthScoreDto healthScoreDTO) {
        return healthScoreService.updateHealthScore(id, healthScoreDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHealthScore(@PathVariable Long id) {
        healthScoreService.deleteHealthScore(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
