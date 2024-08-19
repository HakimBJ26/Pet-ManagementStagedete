package com.example.PetgoraBackend.service.petData;

import com.example.PetgoraBackend.dto.petData.HealthScoreDto;

public interface HealthScoreService {

    HealthScoreDto getHealthScoreByPetId(Integer petId);
    HealthScoreDto saveHealthScore(HealthScoreDto healthScoreDTO);
    HealthScoreDto updateHealthScore(Long id, HealthScoreDto healthScoreDTO);
    void deleteHealthScore(Long id);
    void saveOrUpdateHealthScore(HealthScoreDto healthScoreDTO);
}
