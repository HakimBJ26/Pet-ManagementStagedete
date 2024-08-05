package com.example.PetgoraBackend.service.implementations.alerts;


import com.example.PetgoraBackend.dto.alerts.HealthAlertDTO;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.entity.alerts.HealthAlert;
import com.example.PetgoraBackend.exception.RessourceNotFoundException;
import com.example.PetgoraBackend.mapper.alerts.HealthAlertMapper;
import com.example.PetgoraBackend.repository.alerts.HealthAlertRepository;
import com.example.PetgoraBackend.repository.PetRepo;
import com.example.PetgoraBackend.service.alerts.HealthAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HealthAlertServiceImpl implements HealthAlertService {

    @Autowired
    private HealthAlertRepository healthAlertRepository;

    @Autowired
    private PetRepo petRepository;

    @Autowired
    private HealthAlertMapper healthAlertMapper;

    @Override
    public List<HealthAlertDTO> getHealthAlertsByPetId(Integer petId) {
        List<HealthAlert> healthAlerts = healthAlertRepository.findAll().stream()
                .filter(alert -> alert.getPet().getId().equals(petId))
                .collect(Collectors.toList());
        return healthAlerts.stream().map(healthAlertMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public HealthAlertDTO getHealthAlertById(Integer id) {
        HealthAlert healthAlert = healthAlertRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("HealthAlert not found"));
        return healthAlertMapper.toDTO(healthAlert);
    }

    @Override
    public void deleteHealthAlertById(Integer id) {
        HealthAlert healthAlert = healthAlertRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("HealthAlert not found"));
        healthAlertRepository.delete(healthAlert);
    }

    @Override
    public HealthAlertDTO saveHealthAlert(HealthAlertDTO healthAlertDTO) {
        Pet pet = petRepository.findById(healthAlertDTO.getPetId())
                .orElseThrow(() -> new RessourceNotFoundException("Pet not found"));
        HealthAlert healthAlert = healthAlertMapper.toEntity(healthAlertDTO, pet);
        HealthAlert savedHealthAlert = healthAlertRepository.save(healthAlert);
        return healthAlertMapper.toDTO(savedHealthAlert);
    }
}

