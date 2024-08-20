package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.dto.PetDto;
import com.example.PetgoraBackend.dto.PositionPetDto;
import com.example.PetgoraBackend.dto.SafeZoneDto;
import com.example.PetgoraBackend.entity.Position;
import com.example.PetgoraBackend.entity.SafeZoneType;

import java.util.List;
import java.util.Map;

public interface IPetSafeZoneService {
   PetDto addSafeZonesToPet(Integer petId, Map<SafeZoneType, List<PositionPetDto>> safeZoneDtos);
 // PetDto addSafeZoneToPet(Integer petId, List<PositionPetDto> positionDtos);
 // PetDto updateSafeZone(Integer petId, Long safeZoneId, List<PositionPetDto> positionDtos);
 PetDto updateSafeZoneByType(Integer petId, SafeZoneType type, List<PositionPetDto> positionDtos);
  PetDto deleteSafeZone(Integer petId, Long safeZoneId);
  List<SafeZoneDto> getSafeZonesByPet(Integer petId);
     List<Position> getPositionsByPetIdAndType(Integer petId, SafeZoneType type);

}