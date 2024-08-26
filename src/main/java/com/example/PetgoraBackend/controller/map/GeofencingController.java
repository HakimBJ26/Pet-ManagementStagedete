package com.example.PetgoraBackend.controller.map;

import com.example.PetgoraBackend.config.LocationWebSocketHandler;
import com.example.PetgoraBackend.dto.PositionPetDto;
import com.example.PetgoraBackend.dto.SafeZoneDto;
import com.example.PetgoraBackend.entity.Position;
import com.example.PetgoraBackend.service.implementations.PetSafeZoneServiceImp;
import com.example.PetgoraBackend.service.map.WebSocketService;
import com.example.PetgoraBackend.service.map.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pet")

public class GeofencingController {

    @Autowired
    private PetSafeZoneServiceImp petSafeZoneServiceImp;
    private final LocationWebSocketHandler locationWebSocketHandler;
  private final ZoneService zoneService;
    private final WebSocketService webSocketService;

    @Autowired
    public GeofencingController(PetSafeZoneServiceImp petSafeZoneService, ZoneService zoneService, LocationWebSocketHandler locationWebSocketHandler, WebSocketService webSocketService) {
        this.petSafeZoneServiceImp = petSafeZoneService;
        this.zoneService = zoneService;
        this.locationWebSocketHandler = locationWebSocketHandler;
        this.webSocketService = webSocketService;
    }

    @GetMapping("/check-pet-in-safe-zone/{petId}")
    public ResponseEntity<Boolean> checkPetInSafeZone(@PathVariable Integer petId) {
        try {
            System.out.println("Vérification des zones de sécurité pour le pet ID: " + petId);
            Position currentPosition = webSocketService.getCurrentPosition(petId);

            System.out.println("Position actuelle du pet: " + currentPosition);

            if (currentPosition == null) {
                System.out.println("Position actuelle non trouvée pour le pet ID: " + petId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
            }

            PositionPetDto currentPositionDto = new PositionPetDto(currentPosition.getLat(), currentPosition.getLng());
            List<SafeZoneDto> safeZones = petSafeZoneServiceImp.getSafeZonesByPet(petId);
            for (SafeZoneDto zone : safeZones) {
                if (zoneService.isPointInPolygon(currentPositionDto, zone.positions())) {
                    System.out.println("Le pet est dans une zone de sécurité.");
                    return ResponseEntity.ok(true);
                }
            }
            System.out.println("Le pet n'est dans aucune zone de sécurité.");
            return ResponseEntity.ok(false);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }
}