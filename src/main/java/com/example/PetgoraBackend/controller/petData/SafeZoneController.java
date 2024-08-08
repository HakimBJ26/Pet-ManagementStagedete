package com.example.PetgoraBackend.controller.petData;

import com.example.PetgoraBackend.dto.PetDto;
import com.example.PetgoraBackend.dto.SafeZoneDto;
import com.example.PetgoraBackend.service.IPetSafeZoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.PetgoraBackend.dto.PositionPetDto;


import java.util.List;

@RestController
@RequestMapping("/api/pets/map")
public class SafeZoneController {

    private final IPetSafeZoneService mapService;

    public SafeZoneController(IPetSafeZoneService mapService) {
        this.mapService = mapService;}


    @PostMapping("/set-safe-zones/{petId}")
    public ResponseEntity<PetDto> addSafeZone(
            @PathVariable Integer petId,
            @RequestBody List<PositionPetDto> positionDtos) {
        PetDto updatedPet = mapService.addSafeZoneToPet(petId, positionDtos);
        return ResponseEntity.ok(updatedPet);}

    @PutMapping("/{petId}/update-safe-zones/{safeZoneId}")
    public ResponseEntity<PetDto> updateSafeZone(
            @PathVariable Integer petId,
            @PathVariable Long safeZoneId,
            @RequestBody List<PositionPetDto> positionDtos) {
        PetDto updatedPet = mapService.updateSafeZone(petId, safeZoneId, positionDtos);
        return ResponseEntity.ok(updatedPet);}

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

}
