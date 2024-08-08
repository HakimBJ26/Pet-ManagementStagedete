package com.example.PetgoraBackend.controller;

import com.example.PetgoraBackend.dto.HealthPassportDto;
import com.example.PetgoraBackend.service.IHealthPassportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health_passports")
public class HealthPassportController {

    private final IHealthPassportService healthPassportService;

    public HealthPassportController(IHealthPassportService healthPassportService) {
        this.healthPassportService = healthPassportService;
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<HealthPassportDto> getHealthPassportByPetId(@PathVariable Integer petId) {
        return ResponseEntity.ok(healthPassportService.getHealthPassportByPetId(petId));
    }
}
