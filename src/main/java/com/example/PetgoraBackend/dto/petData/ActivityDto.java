package com.example.PetgoraBackend.dto.petData;

import java.time.LocalDateTime;

public class ActivityDto {

    private Integer id;
    private String averageBurn;
    private String healthScore;
    private String timeSpentInActivity;
    private String heartRate;
    private LocalDateTime lastUpdated;
    private String steps;
    private Integer petId; // Assuming you want to link this DTO to a Pet entity

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAverageBurn() {
        return averageBurn;
    }

    public void setAverageBurn(String averageBurn) {
        this.averageBurn = averageBurn;
    }

    public String getHealthScore() {
        return healthScore;
    }

    public void setHealthScore(String healthScore) {
        this.healthScore = healthScore;
    }

    public String getTimeSpentInActivity() {
        return timeSpentInActivity;
    }

    public void setTimeSpentInActivity(String timeSpentInActivity) {
        this.timeSpentInActivity = timeSpentInActivity;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }
}
