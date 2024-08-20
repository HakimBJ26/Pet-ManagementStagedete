package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.dto.DangerZoneDto;
import com.example.PetgoraBackend.dto.PetDto;
import com.example.PetgoraBackend.dto.PositionPetDto;

import java.util.List;

public interface IPetDangerZoneService {
    List<DangerZoneDto> getDangerZonesByPet(Integer petId);
     PetDto addDangerZoneToPet(Integer petId, List<PositionPetDto> positionDtos);
     PetDto deleteDangerZone(Integer petId, Long dangerZoneId);
    PetDto updateDangerZone(Integer petId, Long dangerZoneId, List<PositionPetDto> positionDtos);



}
