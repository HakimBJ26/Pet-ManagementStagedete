package com.example.PetgoraBackend.controller.petData;


import com.example.PetgoraBackend.dto.petData.SleepPatternDTO;
import com.example.PetgoraBackend.service.petData.SleepPatternService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sleep-patterns")
public class SleepPatternController {

    private final SleepPatternService sleepPatternService;

    public SleepPatternController(SleepPatternService sleepPatternService) {
        this.sleepPatternService = sleepPatternService;
    }

    @PostMapping
    public ResponseEntity<SleepPatternDTO> createSleepPattern(@RequestBody SleepPatternDTO sleepPatternDTO) {
        SleepPatternDTO createdSleepPattern = sleepPatternService.createSleepPattern(sleepPatternDTO);
        return ResponseEntity.ok(createdSleepPattern);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<SleepPatternDTO>> getSleepPatternsByPetId(@PathVariable Long petId) {
        List<SleepPatternDTO> sleepPatterns = sleepPatternService.getSleepPatternsByPetId(petId);
        return ResponseEntity.ok(sleepPatterns);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSleepPattern(@PathVariable Long id) {
        sleepPatternService.deleteSleepPattern(id);
        return ResponseEntity.noContent().build();
    }
}
