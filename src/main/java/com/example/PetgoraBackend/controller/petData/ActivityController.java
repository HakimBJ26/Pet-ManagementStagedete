package com.example.PetgoraBackend.controller.petData;

import com.example.PetgoraBackend.dto.petData.ActivityDto;
import com.example.PetgoraBackend.service.petData.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/pet/{petId}")
    public ResponseEntity<ActivityDto> getActivityByPetId(@PathVariable Integer petId) {
        ActivityDto activityDto = activityService.getActivityByPetId(petId);
        if (activityDto != null) {
            return new ResponseEntity<>(activityDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ActivityDto> saveActivity(@RequestBody ActivityDto activityDTO) {
        ActivityDto savedActivity = activityService.saveActivity(activityDTO);
        return new ResponseEntity<>(savedActivity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityDto> updateActivity(@PathVariable Integer id, @RequestBody ActivityDto activityDTO) {
        ActivityDto updatedActivity = activityService.updateActivity(id, activityDTO);
        if (updatedActivity != null) {
            return new ResponseEntity<>(updatedActivity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Integer id) {
        activityService.deleteActivity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
