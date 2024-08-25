package com.example.PetgoraBackend.service.map;

import com.example.PetgoraBackend.entity.Position;
import com.example.PetgoraBackend.repository.PetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketService {

    private final Map<Integer, Position> petPositions = new ConcurrentHashMap<>();

    public void updatePetPosition(Integer petId, Position position) {
        petPositions.put(petId, position);
        System.out.println("Position mise à jour pour le pet ID " + petId + ": " + position);
    }

    public Position getCurrentPosition(Integer petId) {
        Position position = petPositions.get(petId);
        if (position == null) {
            System.out.println("Aucune position trouvée pour le pet ID " + petId);
        } else {
            System.out.println("Position trouvée pour le pet ID " + petId + ": " + position);
        }
        return position;
    }


}