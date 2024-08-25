package com.example.PetgoraBackend.entity.petData;

import com.example.PetgoraBackend.entity.Pet;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String averageBurn;
    private String healthScore;
    private String timeSpentInActivity;
    private String heartRate;
    private LocalDateTime lastUpdated;
    private String steps;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE) // Use MERGE to avoid detachment issues
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;

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

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setAvergeBurn(String averageBurn) {
    }
}
