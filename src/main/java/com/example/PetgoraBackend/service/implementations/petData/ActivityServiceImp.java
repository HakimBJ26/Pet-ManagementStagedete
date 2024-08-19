package com.example.PetgoraBackend.service.petData;

import com.example.PetgoraBackend.dto.petData.ActivityDto;
import com.example.PetgoraBackend.entity.petData.Activity;
import com.example.PetgoraBackend.repository.petData.ActivityRepository;
import com.example.PetgoraBackend.repository.petData.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImp implements ActivityService {

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
        Activity activity = new Activity();
        activity.setAverageBurn(activityDTO.getAverageBurn());
        activity.setHealthScore(activityDTO.getHealthScore());
        activity.setTimeSpentInActivity(activityDTO.getTimeSpentInActivity());
        activity.setHeartRate(activityDTO.getHeartRate());
        activity.setLastUpdated(activityDTO.getLastUpdated());
        activity.setSteps(activityDTO.getSteps());
        activityRepo.save(activity);
        return toDto(activity);
    }

    @Override
    public void deleteActivity(Integer id) {
        activityRepo.deleteById(id);
    }

    @Override
    public void saveOrUpdateActivity(Activity activity) {

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
