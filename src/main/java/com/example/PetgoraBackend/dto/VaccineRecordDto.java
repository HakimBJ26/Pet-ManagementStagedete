package com.example.PetgoraBackend.dto;

import java.time.LocalDate;

public record VaccineRecordDto(

        Integer id,
        String vaccineName,
        String description,
        LocalDate date
) {}