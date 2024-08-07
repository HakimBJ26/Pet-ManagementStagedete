package com.example.PetgoraBackend.dto;

import java.time.LocalDate;

public record HealthReminderDto(
        Integer id,
        String description,
        LocalDate date
) {}
