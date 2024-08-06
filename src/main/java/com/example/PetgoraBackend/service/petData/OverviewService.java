package com.example.PetgoraBackend.service.petData;


import com.example.PetgoraBackend.dto.petData.OverviewDto;

public interface OverviewService {

    OverviewDto getOverviewByPetId(Integer petId);
    OverviewDto saveOverview(OverviewDto overviewDTO);
    OverviewDto updateOverview(Integer id, OverviewDto overviewDTO);
    void deleteOverview(Integer id);
    OverviewDto deleteRecentActivity(Integer id, String activity);
}

