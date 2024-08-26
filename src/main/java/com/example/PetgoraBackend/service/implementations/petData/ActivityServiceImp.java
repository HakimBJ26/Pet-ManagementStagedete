package com.example.PetgoraBackend.service.implementations.petData;

import com.example.PetgoraBackend.dto.petData.ActivityDto;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.entity.petData.Activity;
import com.example.PetgoraBackend.repository.PetRepo;
import com.example.PetgoraBackend.repository.petData.ActivityRepository;
import com.example.PetgoraBackend.service.petData.ActivityService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImp implements ActivityService {
    @Autowired
    private PetRepo petRepo;
    @Autowired
    private ActivityRepository activityRepo;

    @Override
    public ActivityDto getActivityByPetId(Integer petId) {
        Activity activity = activityRepo.findByPetId(petId);
        if (activity != null) {
            return toDto(activity);
        }
        return null;
    }

    @Override
    public ActivityDto updateActivity(Integer id, ActivityDto activityDTO) {
        Activity activity = activityRepo.findById(id).orElse(null);
        if (activity != null) {
            activity.setAverageBurn(activityDTO.getAverageBurn());
            activity.setHealthScore(activityDTO.getHealthScore());
            activity.setTimeSpentInActivity(activityDTO.getTimeSpentInActivity());
            activity.setHeartRate(activityDTO.getHeartRate());
            activity.setLastUpdated(activityDTO.getLastUpdated());
            activity.setSteps(activityDTO.getSteps());
            activityRepo.save(activity);
            return toDto(activity);
        }
        return null;
    }

    @Override
    public ActivityDto saveActivity(ActivityDto activityDTO) {

        Pet pet = petRepo.findById(activityDTO.getPetId()).orElseThrow(() ->
                new RuntimeException("Pet not found with id: " + activityDTO.getPetId()));


        Activity activity = new Activity();
        activity.setAverageBurn(activityDTO.getAverageBurn());
        activity.setHealthScore(activityDTO.getHealthScore());
        activity.setTimeSpentInActivity(activityDTO.getTimeSpentInActivity());
        activity.setHeartRate(activityDTO.getHeartRate());
        activity.setLastUpdated(activityDTO.getLastUpdated());
        activity.setSteps(activityDTO.getSteps());
        activity.setPet(pet);

        activityRepo.save(activity);

        // Retourner le DTO
        return toDto(activity);
    }

    @Override
    public void deleteActivity(Integer id) {
        activityRepo.deleteById(id);
    }

    @Override
    public void saveOrUpdateActivity(Activity activity) {
        // Retrieve the Pet entity associated with the Activity
        Pet pet = petRepo.findById(activity.getPet().getId())
                .orElseThrow(() -> new EntityNotFoundException("Pet not found"));

        // Attach the managed Pet entity to the Activity
        activity.setPet(pet);


        Activity existingActivity = activityRepo.findByPetId(pet.getId());
        if (existingActivity != null) {
            // Update the existing Activity record with new values
            existingActivity.setAverageBurn(activity.getAverageBurn());
            existingActivity.setHealthScore(activity.getHealthScore());
            existingActivity.setTimeSpentInActivity(activity.getTimeSpentInActivity());
            existingActivity.setHeartRate(activity.getHeartRate());
            existingActivity.setLastUpdated(activity.getLastUpdated());
            existingActivity.setSteps(activity.getSteps());

            // Save the updated Activity record
            activityRepo.save(existingActivity);
        } else {
            // Save the new Activity record if it doesn't exist
            activityRepo.save(activity);
        }
    }


    private ActivityDto toDto(Activity activity) {
        ActivityDto dto = new ActivityDto();
        dto.setId(activity.getId());
        dto.setAverageBurn(activity.getAverageBurn());
        dto.setHealthScore(activity.getHealthScore());
        dto.setTimeSpentInActivity(activity.getTimeSpentInActivity());
        dto.setHeartRate(activity.getHeartRate());
        dto.setLastUpdated(activity.getLastUpdated());
        dto.setSteps(activity.getSteps());
        return dto;
    }
}
