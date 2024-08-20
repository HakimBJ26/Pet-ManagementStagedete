package com.example.PetgoraBackend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "medical_record")
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "medicament_name")
    private String recordType;

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

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
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
