package com.example.PetgoraBackend.dto;

import java.time.LocalDate;

public record MedicalRecordDto(
        Integer id,
        String recordType,
        String description,
        LocalDate date
) {}
