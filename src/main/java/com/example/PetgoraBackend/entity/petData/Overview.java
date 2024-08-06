package com.example.PetgoraBackend.entity.petData;

import com.example.PetgoraBackend.entity.Pet;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "overview")
public class Overview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ElementCollection
    @CollectionTable(name = "overview_recent_activities", joinColumns = @JoinColumn(name = "overview_id"))
    @Column(name = "activity")
    private List<String> recentActivity;

    private LocalDate nextCheckUp;

    @Enumerated(EnumType.STRING)
    private HealthStatus healthStatus;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pet_id", referencedColumnName = "id", unique = true)
    private Pet pet;

    // Getters and setters
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

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public enum HealthStatus {
        GOOD, FAIR, POOR, TERRIBLE
    }
}
