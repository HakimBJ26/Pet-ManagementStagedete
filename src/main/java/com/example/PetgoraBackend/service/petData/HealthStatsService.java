package com.example.PetgoraBackend.service.petData;

import com.example.PetgoraBackend.dto.petData.HealthStatsDTO;

import java.util.List;

public interface HealthStatsService {
    public List<HealthStatsDTO> getRecentHealthStatsByPetId(Integer petId) ;


    HealthStatsDTO addHealthStats(HealthStatsDTO healthStatsDTO);
}
