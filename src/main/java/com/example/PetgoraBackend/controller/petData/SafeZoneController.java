package com.example.PetgoraBackend.controller.petData;

import com.example.PetgoraBackend.dto.PetDto;
import com.example.PetgoraBackend.dto.SafeZoneDto;
import com.example.PetgoraBackend.entity.Position;
import com.example.PetgoraBackend.entity.SafeZoneType;
import com.example.PetgoraBackend.service.IPetSafeZoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.PetgoraBackend.dto.PositionPetDto;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pets/map")
public class SafeZoneController {

    private final IPetSafeZoneService mapService;

    public SafeZoneController(IPetSafeZoneService mapService) {
        this.mapService = mapService;}


    @PostMapping("/set-safe-zones/{petId}")
    public ResponseEntity<PetDto> addSafeZones(
            @PathVariable Integer petId,
            @RequestBody Map<SafeZoneType, List<PositionPetDto>> safeZoneDtos) {
        PetDto updatedPet = mapService.addSafeZonesToPet(petId, safeZoneDtos);
        return ResponseEntity.ok(updatedPet);
    }

    @GetMapping("/{petId}/type/{type}")
    public ResponseEntity<List<Position>> getPositionsByPetIdAndType(
            @PathVariable Integer petId, @PathVariable SafeZoneType type) {
        System.out.println("Fetching positions for petId: " + petId + " and type: " + type);
        List<Position> positions = mapService.getPositionsByPetIdAndType(petId, type);
        return ResponseEntity.ok(positions);
    }

    @PutMapping("/{petId}/update-home")
    public ResponseEntity<PetDto> updateHomeSafeZone(
            @PathVariable Integer petId,
            @RequestBody List<PositionPetDto> positionDtos) {
        PetDto updatedPet = mapService.updateSafeZoneByType(petId, SafeZoneType.HOME, positionDtos);
        return ResponseEntity.ok(updatedPet);
    }

    @PutMapping("/{petId}/update-vet")
    public ResponseEntity<PetDto> updateVetSafeZone(
            @PathVariable Integer petId,
            @RequestBody List<PositionPetDto> positionDtos) {
        PetDto updatedPet = mapService.updateSafeZoneByType(petId, SafeZoneType.VET, positionDtos);
        return ResponseEntity.ok(updatedPet);
    }

    @PutMapping("/{petId}/update-park")
    public ResponseEntity<PetDto> updateParkSafeZone(
            @PathVariable Integer petId,
            @RequestBody List<PositionPetDto> positionDtos) {
        PetDto updatedPet = mapService.updateSafeZoneByType(petId, SafeZoneType.PARK, positionDtos);
        return ResponseEntity.ok(updatedPet);
    }
    @GetMapping("/{petId}/get-safe-zones")
    public ResponseEntity<List<SafeZoneDto>> getSafeZones(@PathVariable Integer petId) {
        List<SafeZoneDto> safeZones = mapService.getSafeZonesByPet(petId);
        return ResponseEntity.ok(safeZones);
    }

    @DeleteMapping("/{petId}/delete-safe-zones/{safeZoneId}")
    public ResponseEntity<PetDto> deleteSafeZone(
            @PathVariable Integer petId,
            @PathVariable Long safeZoneId) {
        PetDto updatedPet = mapService.deleteSafeZone(petId, safeZoneId);
        return ResponseEntity.ok(updatedPet);
    }

    @GetMapping("/{petId}/home")
    public ResponseEntity<List<Position>> getHomePositions(@PathVariable Integer petId) {
        System.out.println("Received petId for home positions: " + petId);
        List<Position> positions = mapService.getPositionsByPetIdAndType(petId, SafeZoneType.HOME);
        return ResponseEntity.ok(positions);
    }

    @GetMapping("/{petId}/vet")
    public ResponseEntity<List<Position>> getVetPositions(@PathVariable Integer petId) {
        List<Position> positions = mapService.getPositionsByPetIdAndType(petId, SafeZoneType.VET);
        return ResponseEntity.ok(positions);
    }

    @GetMapping("/{petId}/park")
    public ResponseEntity<List<Position>> getParkPositions(@PathVariable Integer petId) {
        List<Position> positions = mapService.getPositionsByPetIdAndType(petId, SafeZoneType.PARK);
        return ResponseEntity.ok(positions);
    }

}
