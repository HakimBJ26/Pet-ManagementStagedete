package com.example.PetgoraBackend.service.implementations;

import com.example.PetgoraBackend.dto.HealthReminderDto;
import com.example.PetgoraBackend.entity.HealthPassport;
import com.example.PetgoraBackend.entity.HealthReminder;
import com.example.PetgoraBackend.mapper.HealthReminderMapper;
import com.example.PetgoraBackend.repository.HealthPassportRepo;
import com.example.PetgoraBackend.repository.HealthReminderRepo;
import com.example.PetgoraBackend.service.IHealthReminderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HealthReminderServiceImp implements IHealthReminderService {

    private final HealthReminderRepo healthReminderRepo;
    private final HealthPassportRepo healthPassportRepo;

    public HealthReminderServiceImp(HealthReminderRepo healthReminderRepo, HealthPassportRepo healthPassportRepo) {
        this.healthReminderRepo = healthReminderRepo;
        this.healthPassportRepo = healthPassportRepo;
    }

    @Override
    public HealthReminderDto createHealthReminder(Integer healthPassportId, HealthReminderDto healthReminderDto) {
        HealthPassport healthPassport = healthPassportRepo.findById(healthPassportId)
                .orElseThrow(() -> new EntityNotFoundException("Health Passport not found for ID " + healthPassportId));
        HealthReminder healthReminder = HealthReminderMapper.INSTANCE.toHealthReminder(healthReminderDto);
        healthReminder.setHealthPassport(healthPassport);
        healthReminder = healthReminderRepo.save(healthReminder);
        return HealthReminderMapper.INSTANCE.toHealthReminderDto(healthReminder);
    }

    @Override
    public HealthReminderDto updateHealthReminder(Integer id, HealthReminderDto healthReminderDto) {
        HealthReminder healthReminder = healthReminderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Health Reminder not found for ID " + id));
        HealthReminderMapper.INSTANCE.updateHealthReminderFromDto(healthReminderDto, healthReminder);
        healthReminder = healthReminderRepo.save(healthReminder);
        return HealthReminderMapper.INSTANCE.toHealthReminderDto(healthReminder);
    }

    @Override
    public void deleteHealthReminder(Integer id) {
        if (!healthReminderRepo.existsById(id)) {
            throw new EntityNotFoundException("Health Reminder not found for ID " + id);
        }
        healthReminderRepo.deleteById(id);
    }

    @Override
    public HealthReminderDto getHealthReminderById(Integer id) {
        HealthReminder healthReminder = healthReminderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Health Reminder not found for ID " + id));
        return HealthReminderMapper.INSTANCE.toHealthReminderDto(healthReminder);
    }

    @Override
    public List<HealthReminderDto> getAllHealthRemindersByHealthPassportId(Integer healthPassportId) {
        List<HealthReminder> healthReminders = healthReminderRepo.findAllByHealthPassportId(healthPassportId);
        return healthReminders.stream()
                .map(HealthReminderMapper.INSTANCE::toHealthReminderDto)
                .collect(Collectors.toList());
    }
}
