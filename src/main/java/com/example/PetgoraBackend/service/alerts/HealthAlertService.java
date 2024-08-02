package com.example.PetgoraBackend.service.alerts;


import com.example.PetgoraBackend.dto.alerts.HealthAlertDTO;

import java.util.List;

public interface HealthAlertService {

    List<HealthAlertDTO> getHealthAlertsByPetId(Integer petId);

    HealthAlertDTO getHealthAlertById(Integer id);

    HealthAlertDTO saveHealthAlert(HealthAlertDTO healthAlertDTO);


    void deleteHealthAlertById(Integer id);
}

