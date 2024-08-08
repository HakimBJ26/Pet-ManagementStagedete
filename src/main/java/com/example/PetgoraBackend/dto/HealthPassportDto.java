package com.example.PetgoraBackend.dto;

import java.util.List;

public record HealthPassportDto(
        Integer id,
        Integer petId,
        List<VaccineRecordDto> vaccineRecords,
        List<MedicalRecordDto> medicalRecords,
        List<SurgeryRecordDto> surgeryRecords,
        List<VisitRecordDto> visitRecords,
        List<HealthReminderDto> healthReminders
) {}
