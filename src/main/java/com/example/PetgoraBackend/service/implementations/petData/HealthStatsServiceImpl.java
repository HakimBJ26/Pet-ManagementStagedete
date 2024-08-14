package com.example.PetgoraBackend.service.implementations.petData;

import com.example.PetgoraBackend.dto.petData.HealthStatsDTO;
import com.example.PetgoraBackend.entity.petData.HealthStats;
import com.example.PetgoraBackend.mapper.petData.HealthStatsMapper;
import com.example.PetgoraBackend.repository.PetRepo;
import com.example.PetgoraBackend.repository.petData.HealthStatsRepository;
import com.example.PetgoraBackend.service.petData.HealthStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HealthStatsServiceImpl implements HealthStatsService {

    @Autowired
    private HealthStatsRepository healthStatsRepository;

    @Autowired
    private PetRepo petRepository;

    @Autowired
    private HealthStatsMapper healthStatsMapper;


    @Override
    public List<HealthStatsDTO> getRecentHealthStatsByPetId(Integer petId) {
        // Create Pageable with sorting by recordDate in descending order
        Pageable pageable = PageRequest.of(0, 16, Sort.by(Sort.Direction.DESC, "recordDate"));
        Page<HealthStats> healthStatsPage = healthStatsRepository.findByPetId(petId, pageable);
        List<HealthStats> healthStatsList = healthStatsPage.getContent();
        return healthStatsList.stream()
                .map(healthStatsMapper::toDTO)
                .collect(Collectors.toList());
    }






    @Override
    public HealthStatsDTO addHealthStats(HealthStatsDTO healthStatsDTO) {
        HealthStats healthStats = healthStatsMapper.toEntity(healthStatsDTO);
        healthStats.setPet(
                petRepository.findById(healthStatsDTO.getPetId())
                        .orElseThrow(() -> new RuntimeException("Pet not found"))
        );
        healthStats = healthStatsRepository.save(healthStats);
        return healthStatsMapper.toDTO(healthStats);
    }
}
