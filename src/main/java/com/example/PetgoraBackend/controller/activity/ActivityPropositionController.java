package com.example.PetgoraBackend.controller.activity;

import com.example.PetgoraBackend.dto.activity.ActivityPropositionDTO;
import com.example.PetgoraBackend.service.activity.ActivityPropositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity-propositions")
public class ActivityPropositionController {

    private final ActivityPropositionService activityPropositionService;

    public ActivityPropositionController(ActivityPropositionService activityPropositionService) {
        this.activityPropositionService = activityPropositionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ActivityPropositionDTO> createActivityProposition(@RequestBody ActivityPropositionDTO activityPropositionDTO) {
        ActivityPropositionDTO createdActivityProposition = activityPropositionService.createActivityProposition(activityPropositionDTO);
        return ResponseEntity.ok(createdActivityProposition);
    }

    @GetMapping
    public ResponseEntity<List<ActivityPropositionDTO>> getActivityPropositionsByPetId() {
        List<ActivityPropositionDTO> activityPropositions = activityPropositionService.getActivityPropositions();
        return ResponseEntity.ok(activityPropositions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivityProposition(@PathVariable Long id) {
        activityPropositionService.deleteActivityProposition(id);
        return ResponseEntity.noContent().build();
    }
}

