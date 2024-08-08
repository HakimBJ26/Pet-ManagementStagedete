package com.example.PetgoraBackend.controller.petData;

import com.example.PetgoraBackend.dto.DangerZoneDto;
import com.example.PetgoraBackend.dto.PetDto;
import com.example.PetgoraBackend.dto.PositionPetDto;
import com.example.PetgoraBackend.service.IPetDangerZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets/map")
public class DangerZoneController {


    private final IPetDangerZoneService dangerZoneService;

    @Autowired
    public DangerZoneController(IPetDangerZoneService dangerZoneService) {
        this.dangerZoneService = dangerZoneService;
    }
        @PostMapping("/{petId}/add-danger-zones")
        public ResponseEntity<PetDto> addDangerZoneToPet(
                @PathVariable Integer petId,
                @RequestBody List<PositionPetDto> positionDtos) {
            PetDto petDto = dangerZoneService.addDangerZoneToPet(petId, positionDtos);
            return ResponseEntity.ok(petDto);
        }

        @PutMapping("/{petId}/update-danger-zones/{dangerZoneId}")
        public ResponseEntity<PetDto> updateDangerZone(
                @PathVariable Integer petId,
                @PathVariable Long dangerZoneId,
                @RequestBody List<PositionPetDto> positionDtos) {
            PetDto petDto = dangerZoneService.updateDangerZone(petId, dangerZoneId, positionDtos);
            return ResponseEntity.ok(petDto);
        }

        @DeleteMapping("/{petId}/delete-danger-zones/{dangerZoneId}")
        public ResponseEntity<PetDto> deleteDangerZone(
                @PathVariable Integer petId,
                @PathVariable Long dangerZoneId) {
            PetDto petDto = dangerZoneService.deleteDangerZone(petId, dangerZoneId);
            return ResponseEntity.ok(petDto);
        }

        @GetMapping("/{petId}/get-danger-zones")
        public ResponseEntity<List<DangerZoneDto>> getDangerZonesByPet(
                @PathVariable Integer petId) {
            List<DangerZoneDto> dangerZoneDtos = dangerZoneService.getDangerZonesByPet(petId);
            return ResponseEntity.ok(dangerZoneDtos);
        }
    }






