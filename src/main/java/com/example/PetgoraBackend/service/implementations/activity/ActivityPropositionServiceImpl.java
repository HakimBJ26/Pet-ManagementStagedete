package com.example.PetgoraBackend.service.implementations.activity;


import com.example.PetgoraBackend.dto.activity.ActivityPropositionDTO;
import com.example.PetgoraBackend.entity.acitvity.ActivityProposition;
import com.example.PetgoraBackend.mapper.activity.ActivityPropositionMapper;
import com.example.PetgoraBackend.repository.activity.ActivityPropositionRepository;
import com.example.PetgoraBackend.service.activity.ActivityPropositionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ActivityPropositionServiceImpl implements ActivityPropositionService {

    private final ActivityPropositionRepository activityPropositionRepository;
    private final ActivityPropositionMapper activityPropositionMapper;

    public ActivityPropositionServiceImpl(ActivityPropositionRepository activityPropositionRepository, ActivityPropositionMapper activityPropositionMapper) {
        this.activityPropositionRepository = activityPropositionRepository;
        this.activityPropositionMapper = activityPropositionMapper;
    }

    @Override
    public ActivityPropositionDTO createActivityProposition(ActivityPropositionDTO activityPropositionDTO) {
        ActivityProposition activityProposition = activityPropositionMapper.toEntity(activityPropositionDTO);
        ActivityProposition savedActivityProposition = activityPropositionRepository.save(activityProposition);
        return activityPropositionMapper.toDto(savedActivityProposition);
    }

    @Override
    public List<ActivityPropositionDTO> getActivityPropositions() {
        List<ActivityProposition> activityPropositions = activityPropositionRepository.findTop2ByOrderByDayDesc();
        return activityPropositions.stream()
                .map(activityPropositionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteActivityProposition(Long activityPropositionId) {
        activityPropositionRepository.deleteById(activityPropositionId);
    }
}

