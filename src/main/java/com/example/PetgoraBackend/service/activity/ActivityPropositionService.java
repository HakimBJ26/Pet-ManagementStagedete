package com.example.PetgoraBackend.service.activity;



import com.example.PetgoraBackend.dto.activity.ActivityPropositionDTO;

import java.util.List;

public interface ActivityPropositionService {
    ActivityPropositionDTO createActivityProposition(ActivityPropositionDTO activityPropositionDTO);
    List<ActivityPropositionDTO> getActivityPropositions();
    void deleteActivityProposition(Long activityPropositionId);
}
