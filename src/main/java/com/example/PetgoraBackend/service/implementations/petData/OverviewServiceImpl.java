package com.example.PetgoraBackend.service.implementations.petData;


import com.example.PetgoraBackend.dto.petData.OverviewDto;
import com.example.PetgoraBackend.entity.petData.Overview;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.exception.RessourceNotFoundException;
import com.example.PetgoraBackend.mapper.petData.OverviewMapper;
import com.example.PetgoraBackend.repository.petData.OverviewRepository;
import com.example.PetgoraBackend.repository.PetRepo;
import com.example.PetgoraBackend.service.petData.OverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OverviewServiceImpl implements OverviewService {

    @Autowired
    private OverviewRepository overviewRepository;

    @Autowired
    private PetRepo petRepository;

    @Autowired
    private OverviewMapper overviewMapper;

    @Override
    public OverviewDto getOverviewByPetId(Integer petId) {
        Optional<Overview> optionalOverview = overviewRepository.findById(petId);
        return optionalOverview.map(overviewMapper::toDTO).orElse(null);
    }

    @Override
    public OverviewDto saveOverview(OverviewDto overviewDTO) {
        Pet pet = petRepository.findById(overviewDTO.getPetId())
                .orElseThrow(() -> new RessourceNotFoundException("Pet not found"));
        if (overviewRepository.findByPetId(pet.getId()).isPresent()) {
            throw new IllegalStateException("Overview already exists for this pet");
        }
        Overview overview = overviewMapper.toEntity(overviewDTO, pet);
        Overview savedOverview = overviewRepository.save(overview);
        return overviewMapper.toDTO(savedOverview);
    }

    @Override
    public OverviewDto updateOverview(Integer id, OverviewDto overviewDTO) {
        Overview existingOverview = overviewRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Overview not found"));
        Pet pet = petRepository.findById(overviewDTO.getPetId())
                .orElseThrow(() -> new RessourceNotFoundException("Pet not found"));
        existingOverview.setRecentActivity(overviewDTO.getRecentActivity());
        existingOverview.setNextCheckUp(overviewDTO.getNextCheckUp());
        existingOverview.setHealthStatus(Overview.HealthStatus.valueOf(overviewDTO.getHealthStatus()));
        existingOverview.setPet(pet);
        Overview updatedOverview = overviewRepository.save(existingOverview);
        return overviewMapper.toDTO(updatedOverview);
    }

    @Override
    public void deleteOverview(Integer id) {
        if (!overviewRepository.existsById(id)) {
            throw new RessourceNotFoundException("Overview not found");
        }
        overviewRepository.deleteById(id);
    }

    @Override
    public OverviewDto deleteRecentActivity(Integer id, String activity) {
        Overview existingOverview = overviewRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Overview not found"));
        List<String> recentActivities = existingOverview.getRecentActivity();
        recentActivities.remove(activity);
        existingOverview.setRecentActivity(recentActivities);
        Overview updatedOverview = overviewRepository.save(existingOverview);
        return overviewMapper.toDTO(updatedOverview);
    }
}
