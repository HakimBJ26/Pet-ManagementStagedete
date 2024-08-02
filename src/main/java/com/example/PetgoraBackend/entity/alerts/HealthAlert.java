package com.example.PetgoraBackend.entity.alerts;

import com.example.PetgoraBackend.entity.Pet;
import jakarta.persistence.*;

@Entity
@Table(name = "health_alert")
public class HealthAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String severity;
    private String action;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
