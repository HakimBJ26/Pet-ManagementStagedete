package com.example.PetgoraBackend.service.map;

import com.example.PetgoraBackend.dto.PositionPetDto;
import com.example.PetgoraBackend.dto.SafeZoneDto;
import com.example.PetgoraBackend.entity.Position;
import com.example.PetgoraBackend.entity.SafeZone;
import com.example.PetgoraBackend.repository.PetRepo;
import com.example.PetgoraBackend.repository.petData.SafeZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZoneService {
    @Autowired
    private SafeZoneRepository safeZoneRepository;
    @Autowired
    private PetRepo petRepository;

    public boolean isWithinSafeZone(PositionPetDto positionDto, SafeZone safeZone) {
        List<PositionPetDto> zonePositions = safeZone.getPositions().stream()
                .map(pos -> new PositionPetDto(pos.getLat(), pos.getLng()))
                .collect(Collectors.toList());

        // Assurez-vous que la zone de sécurité a au moins 3 positions pour former un polygone.
        if (zonePositions.size() < 3) {
            throw new IllegalArgumentException("La zone de sécurité doit contenir au moins 3 positions.");
        }

        return isPointInPolygon(positionDto, zonePositions);
    }
    public boolean isPointInPolygon(PositionPetDto point, List<PositionPetDto> polygon) {
        // Implémentation de la vérification
        double x = point.lat();
        double y = point.lng();
        boolean inside = false;
        int n = polygon.size();

        for (int i = 0, j = n - 1; i < n; j = i++) {
            double xi = polygon.get(i).lat();
            double yi = polygon.get(i).lng();
            double xj = polygon.get(j).lat();
            double yj = polygon.get(j).lng();

            boolean intersect = ((yi > y) != (yj > y)) && (x < (xj - xi) * (y - yi) / (yj - yi) + xi);
            if (intersect) inside = !inside;
        }
        return inside;
    }


}