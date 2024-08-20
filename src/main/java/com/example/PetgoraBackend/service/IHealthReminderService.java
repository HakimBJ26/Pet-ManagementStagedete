package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.dto.HealthReminderDto;
import java.util.List;

public interface IHealthReminderService {
    HealthReminderDto createHealthReminder(Integer healthPassportId, HealthReminderDto healthReminderDto);
    HealthReminderDto updateHealthReminder(Integer id, HealthReminderDto healthReminderDto);
    void deleteHealthReminder(Integer id);
    HealthReminderDto getHealthReminderById(Integer id);
    List<HealthReminderDto> getAllHealthRemindersByHealthPassportId(Integer healthPassportId);
}
