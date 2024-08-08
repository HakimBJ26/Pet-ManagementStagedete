package com.example.PetgoraBackend.dto;

import java.util.List;

public record DangerZoneDto(Long id, List<PositionPetDto> positions) {}