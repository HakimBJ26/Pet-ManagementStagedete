package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.dto.PetDto;
import com.example.PetgoraBackend.dto.PositionPetDto;
import com.example.PetgoraBackend.dto.SafeZoneDto;

import java.util.List;

public interface IPetSafeZoneService {

  PetDto addSafeZoneToPet(Integer petId, List<PositionPetDto> positionDtos);
  PetDto updateSafeZone(Integer petId, Long safeZoneId, List<PositionPetDto> positionDtos);
  PetDto deleteSafeZone(Integer petId, Long safeZoneId);
  List<SafeZoneDto> getSafeZonesByPet(Integer petId);
}
