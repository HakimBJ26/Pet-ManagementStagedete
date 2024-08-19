package com.example.PetgoraBackend.service.implementations.petData;

import com.example.PetgoraBackend.dto.petData.HealthScoreDto;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.entity.petData.HealthScore;
import com.example.PetgoraBackend.exception.RessourceNotFoundException;
import com.example.PetgoraBackend.mapper.petData.HealthScoreMapper;
import com.example.PetgoraBackend.repository.PetRepo;
import com.example.PetgoraBackend.repository.petData.HealthScoreRepository;
import com.example.PetgoraBackend.service.petData.HealthScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HealthScoreServiceImpl implements HealthScoreService {

    @Autowired
    private HealthScoreRepository healthScoreRepository;

    @Autowired
    private PetRepo petRepository;

    @Autowired
    private HealthScoreMapper healthScoreMapper;

    @Override
    public HealthScoreDto getHealthScoreByPetId(Integer petId) {
        Optional<HealthScore> optionalHealthScore = Optional.ofNullable(healthScoreRepository.findByPetId(petId));
        return optionalHealthScore.map(healthScoreMapper::toDTO).orElseThrow(() -> new RessourceNotFoundException("HealthScore not found"));
    }

    @Override
    public HealthScoreDto saveHealthScore(HealthScoreDto healthScoreDTO) {
        Pet pet = petRepository.findById(healthScoreDTO.getPetId())
                .orElseThrow(() -> new RessourceNotFoundException("Pet not found"));
        HealthScore healthScore = healthScoreMapper.toEntity(healthScoreDTO, pet);
        HealthScore savedHealthScore = healthScoreRepository.save(healthScore);
        return healthScoreMapper.toDTO(savedHealthScore);
    }

    @Override
    public HealthScoreDto updateHealthScore(Long id, HealthScoreDto healthScoreDTO) {
        HealthScore existingHealthScore = healthScoreRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("HealthScore not found"));
        Pet pet = petRepository.findById(healthScoreDTO.getPetId())
                .orElseThrow(() -> new RessourceNotFoundException("Pet not found"));

        existingHealthScore.setAvgHealth(healthScoreDTO.getAvgHealth());
        existingHealthScore.setActivityTime(healthScoreDTO.getActivityTime());
        existingHealthScore.setCalories(healthScoreDTO.getCalories());
        existingHealthScore.setHeartRate(healthScoreDTO.getHeartRate());
        existingHealthScore.setSpeed(healthScoreDTO.getSpeed());
        existingHealthScore.setTimestamp(healthScoreDTO.getTimestamp());
        existingHealthScore.setPet(pet);

        HealthScore updatedHealthScore = healthScoreRepository.save(existingHealthScore);
        return healthScoreMapper.toDTO(updatedHealthScore);
    }

    @Override
    public void deleteHealthScore(Long id) {
        if (!healthScoreRepository.existsById(id)) {
            throw new RessourceNotFoundException("HealthScore not found");
        }
        healthScoreRepository.deleteById(id);
    }

    @Override
    public void saveOrUpdateHealthScore(HealthScoreDto healthScoreDTO) {
        Pet pet = petRepository.findById(healthScoreDTO.getPetId())
                .orElseThrow(() -> new RessourceNotFoundException("Pet not found"));

        HealthScore existingHealthScore = healthScoreRepository.findByPetId(pet.getId());
        if (existingHealthScore != null) {
            existingHealthScore.setAvgHealth(healthScoreDTO.getAvgHealth());
            existingHealthScore.setActivityTime(healthScoreDTO.getActivityTime());
            existingHealthScore.setCalories(healthScoreDTO.getCalories());
            existingHealthScore.setHeartRate(healthScoreDTO.getHeartRate());
            existingHealthScore.setSpeed(healthScoreDTO.getSpeed());
            existingHealthScore.setTimestamp(healthScoreDTO.getTimestamp());
            healthScoreRepository.save(existingHealthScore);
        } else {
            HealthScore healthScore = healthScoreMapper.toEntity(healthScoreDTO, pet);
            healthScoreRepository.save(healthScore);
        }
    }
}
