package com.example.PetgoraBackend.controller;

import com.example.PetgoraBackend.dto.HealthReminderDto;
import com.example.PetgoraBackend.service.IHealthReminderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health_reminders")
public class HealthReminderController {

    private final IHealthReminderService healthReminderService;

    public HealthReminderController(IHealthReminderService healthReminderService) {
        this.healthReminderService = healthReminderService;
    }

    @PostMapping("/{healthPassportId}")
    public HealthReminderDto createHealthReminder(@PathVariable Integer healthPassportId, @RequestBody HealthReminderDto healthReminderDto) {
        return healthReminderService.createHealthReminder(healthPassportId, healthReminderDto);
    }

    @PutMapping("/{id}")
    public HealthReminderDto updateHealthReminder(@PathVariable Integer id, @RequestBody HealthReminderDto healthReminderDto) {
        return healthReminderService.updateHealthReminder(id, healthReminderDto);
    }

    @DeleteMapping("/{id}")
    public void deleteHealthReminder(@PathVariable Integer id) {
        healthReminderService.deleteHealthReminder(id);
    }

    @GetMapping("/{id}")
    public HealthReminderDto getHealthReminderById(@PathVariable Integer id) {
        return healthReminderService.getHealthReminderById(id);
    }

    @GetMapping("/healthPassport/{healthPassportId}")
    public List<HealthReminderDto> getAllHealthRemindersByHealthPassportId(@PathVariable Integer healthPassportId) {
        return healthReminderService.getAllHealthRemindersByHealthPassportId(healthPassportId);
    }
}
