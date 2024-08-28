package com.example.PetgoraBackend.dto;

import java.util.List;

public record SafeZoneDto(Long id, List<PositionPetDto> positions) {}

