package com.example.PetgoraBackend.mapper.petData;

import com.example.PetgoraBackend.dto.petData.HealthScoreDto;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.entity.petData.HealthScore;
import org.springframework.stereotype.Component;

@Component
public class HealthScoreMapper {

    public HealthScoreDto toDTO(HealthScore healthScore) {
        HealthScoreDto dto = new HealthScoreDto();
        dto.setId(healthScore.getId());
        dto.setAvgHealth(healthScore.getAvgHealth());
        dto.setActivityTime(healthScore.getActivityTime());
        dto.setCalories(healthScore.getCalories());
        dto.setHeartRate(healthScore.getHeartRate());
        dto.setSpeed(healthScore.getSpeed());
        dto.setTimestamp(healthScore.getTimestamp());
        dto.setPetId(healthScore.getPet().getId());
        return dto;
    }

    public HealthScore toEntity(HealthScoreDto dto, Pet pet) {
        HealthScore healthScore = new HealthScore();
        healthScore.setId(dto.getId());
        healthScore.setAvgHealth(dto.getAvgHealth());
        healthScore.setActivityTime(dto.getActivityTime());
        healthScore.setCalories(dto.getCalories());
        healthScore.setHeartRate(dto.getHeartRate());
        healthScore.setSpeed(dto.getSpeed());
        healthScore.setTimestamp(dto.getTimestamp());
        healthScore.setPet(pet);
        return healthScore;
    }
}
