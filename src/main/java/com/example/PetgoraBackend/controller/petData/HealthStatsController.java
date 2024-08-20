package com.example.PetgoraBackend.controller.petData;



import com.example.PetgoraBackend.dto.petData.HealthStatsDTO;
import com.example.PetgoraBackend.service.petData.HealthStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health-stats")
public class HealthStatsController {

    @Autowired
    private HealthStatsService healthStatsService;

    @GetMapping("/{petId}")
    public ResponseEntity<List<HealthStatsDTO>> getHealthStatsByPetId(@PathVariable Integer petId) {
        List<HealthStatsDTO> healthStatsList = healthStatsService.getRecentHealthStatsByPetId(petId);
        return ResponseEntity.ok(healthStatsList);
    }

    @PostMapping("/")
    public ResponseEntity<HealthStatsDTO> addHealthStats(@RequestBody HealthStatsDTO healthStatsDTO) {
        HealthStatsDTO savedHealthStats = healthStatsService.addHealthStats(healthStatsDTO);
        return ResponseEntity.ok(savedHealthStats);
    }
}
