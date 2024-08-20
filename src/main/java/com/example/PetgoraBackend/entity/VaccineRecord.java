package com.example.PetgoraBackend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vaccine_record")
public class VaccineRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String vaccineName;
    @Column(name = "veterinarian")
    private String description;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "health_passport_id")
    private HealthPassport healthPassport;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public HealthPassport getHealthPassport() {
        return healthPassport;
    }

    public void setHealthPassport(HealthPassport healthPassport) {
        this.healthPassport = healthPassport;
    }
}
