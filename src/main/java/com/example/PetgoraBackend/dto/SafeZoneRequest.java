package com.example.PetgoraBackend.dto;

import com.example.PetgoraBackend.entity.SafeZoneType;

public record SafeZoneRequest(SafeZoneType type, PositionPetDto positionDto) {}
