package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.dto.HealthPassportDto;

public interface IHealthPassportService {
    HealthPassportDto getHealthPassportByPetId(Integer petId);
}
