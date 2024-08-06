package com.example.PetgoraBackend.dto.petData;


import java.time.LocalDate;
import java.util.List;

public class OverviewDto {

    private Integer id;
    private List<String> recentActivity;
    private LocalDate nextCheckUp;
    private String healthStatus;
    private Integer petId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getRecentActivity() {
        return recentActivity;
    }

    public void setRecentActivity(List<String> recentActivity) {
        this.recentActivity = recentActivity;
    }

    public LocalDate getNextCheckUp() {
        return nextCheckUp;
    }

    public void setNextCheckUp(LocalDate nextCheckUp) {
        this.nextCheckUp = nextCheckUp;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }
}
