
package com.example.PetgoraBackend.entity.petData;

import com.example.PetgoraBackend.entity.Pet;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class HealthScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String avgHealth;
    private String activityTime;
    private String calories;
    private String heartRate;
    private String speed;
    private LocalDateTime timestamp;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE) // Use MERGE to avoid detachment issues
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvgHealth() {
        return avgHealth;
    }

    public void setAvgHealth(String avgHealth) {
        this.avgHealth = avgHealth;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
