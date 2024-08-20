package com.example.PetgoraBackend.entity.alerts;


import com.example.PetgoraBackend.entity.Pet;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_timestamps")
public class NotificationTimestamps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;

    private LocalDateTime lastHeartRateNotification;
    private LocalDateTime lastTemperatureNotification;
    private LocalDateTime lastActivityLevelNotification;

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public LocalDateTime getLastHeartRateNotification() {
        return lastHeartRateNotification;
    }

    public void setLastHeartRateNotification(LocalDateTime lastHeartRateNotification) {
        this.lastHeartRateNotification = lastHeartRateNotification;
    }

    public LocalDateTime getLastTemperatureNotification() {
        return lastTemperatureNotification;
    }

    public void setLastTemperatureNotification(LocalDateTime lastTemperatureNotification) {
        this.lastTemperatureNotification = lastTemperatureNotification;
    }

    public LocalDateTime getLastActivityLevelNotification() {
        return lastActivityLevelNotification;
    }

    public void setLastActivityLevelNotification(LocalDateTime lastActivityLevelNotification) {
        this.lastActivityLevelNotification = lastActivityLevelNotification;
    }
}
