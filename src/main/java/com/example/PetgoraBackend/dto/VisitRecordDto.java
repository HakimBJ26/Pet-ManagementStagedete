package com.example.PetgoraBackend.dto;

import java.time.LocalDate;

public record VisitRecordDto(
        Integer id,
        String visitType,
        String description,
        LocalDate date
) {}
