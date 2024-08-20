package com.example.PetgoraBackend.dto;

import java.time.LocalDate;

public record SurgeryRecordDto(
        Integer id,
        String surgeryType,
        String description,
        LocalDate date
) {}
