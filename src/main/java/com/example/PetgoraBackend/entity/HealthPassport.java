package com.example.PetgoraBackend.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "health_passport")
public class HealthPassport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @OneToMany(mappedBy = "healthPassport", cascade = CascadeType.ALL)
    private List<VaccineRecord> vaccineRecords;

    @OneToMany(mappedBy = "healthPassport", cascade = CascadeType.ALL)
    private List<MedicalRecord> medicalRecords;

    @OneToMany(mappedBy = "healthPassport", cascade = CascadeType.ALL)
    private List<SurgeryRecord> surgeryRecords;

    @OneToMany(mappedBy = "healthPassport", cascade = CascadeType.ALL)
    private List<VisitRecord> visitRecords;

    @OneToMany(mappedBy = "healthPassport", cascade = CascadeType.ALL)
    private List<HealthReminder> healthReminders;

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

    public List<VaccineRecord> getVaccineRecords() {
        return vaccineRecords;
    }

    public void setVaccineRecords(List<VaccineRecord> vaccineRecords) {
        this.vaccineRecords = vaccineRecords;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    public List<SurgeryRecord> getSurgeryRecords() {
        return surgeryRecords;
    }

    public void setSurgeryRecords(List<SurgeryRecord> surgeryRecords) {
        this.surgeryRecords = surgeryRecords;
    }

    public List<VisitRecord> getVisitRecords() {
        return visitRecords;
    }

    public void setVisitRecords(List<VisitRecord> visitRecords) {
        this.visitRecords = visitRecords;
    }

    public List<HealthReminder> getHealthReminders() {
        return healthReminders;
    }

    public void setHealthReminders(List<HealthReminder> healthReminders) {
        this.healthReminders = healthReminders;
    }
}




