package com.example.PetgoraBackend.controller.alerts;


import com.example.PetgoraBackend.dto.alerts.HealthAlertDTO;
import com.example.PetgoraBackend.service.alerts.HealthAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health-alerts")
public class HealthAlertController {

    @Autowired
    private HealthAlertService healthAlertService;

    @GetMapping("/pet/{petId}")
    public List<HealthAlertDTO> getHealthAlertsByPetId(@PathVariable Integer petId) {
        return healthAlertService.getHealthAlertsByPetId(petId);
    }

    @GetMapping("/{id}")
    public HealthAlertDTO getHealthAlertById(@PathVariable Integer id) {
        return healthAlertService.getHealthAlertById(id);
    }

    @PostMapping
    public HealthAlertDTO saveHealthAlert(@RequestBody HealthAlertDTO healthAlertDTO) {
        return healthAlertService.saveHealthAlert(healthAlertDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteHealthAlertById(@PathVariable Integer id) {
        healthAlertService.deleteHealthAlertById(id);
    }
}

