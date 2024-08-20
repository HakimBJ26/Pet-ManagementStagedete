package com.example.PetgoraBackend.service.petData;

import com.example.PetgoraBackend.dto.petData.ActivityDto;
import com.example.PetgoraBackend.entity.petData.Activity;

public interface ActivityService {

    ActivityDto getActivityByPetId(Integer petId);
    ActivityDto updateActivity(Integer id, ActivityDto activityDTO);
    ActivityDto saveActivity(ActivityDto activityDTO);
    void deleteActivity(Integer id);
    void saveOrUpdateActivity(Activity activity);

}
