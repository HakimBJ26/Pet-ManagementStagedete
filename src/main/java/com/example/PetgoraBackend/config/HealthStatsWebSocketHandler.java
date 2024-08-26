package com.example.PetgoraBackend.config;


import com.example.PetgoraBackend.entity.petData.HealthStats;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.repository.PetRepo;
import com.example.PetgoraBackend.repository.petData.HealthStatsRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HealthStatsWebSocketHandler {

    private final HealthStatsRepository healthStatsRepository;
    private final PetRepo petRepository;
    private final ObjectMapper mapper;

    public HealthStatsWebSocketHandler(HealthStatsRepository healthStatsRepository, PetRepo petRepository, ObjectMapper mapper) {
        this.healthStatsRepository = healthStatsRepository;
        this.petRepository = petRepository;
        this.mapper = mapper;
    }

    public void handleHealthStatsData(String message) {
        try {
            JsonNode jsonNode = mapper.readTree(message);
            JsonNode healthStats = jsonNode.get("healthData");

            if (healthStats != null) {
                Integer petId = healthStats.get("petId").asInt();
                Pet pet = petRepository.findById(petId).orElse(null);

                double fat = healthStats.get("fat").asDouble();
                double carb = healthStats.get("carb").asDouble();
                double protein = healthStats.get("protein").asDouble();

                LocalDateTime recordDate = LocalDateTime.now();

                if (pet != null) {
                    HealthStats newHealthStats = new HealthStats();
                    newHealthStats.setPet(pet);
                    newHealthStats.setFat(fat);
                    newHealthStats.setCarb(carb);
                    newHealthStats.setProtein(protein);
                    newHealthStats.setRecordDate(recordDate);
                    healthStatsRepository.save(newHealthStats);

                    System.out.println("Health stats stored for pet " + petId);
                } else {
                    System.out.println("Pet not found for ID " + petId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
