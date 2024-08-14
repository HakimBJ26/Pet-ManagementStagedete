package com.example.PetgoraBackend.entity.petData;


import com.example.PetgoraBackend.entity.Pet;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "health_stats")
public class HealthStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double fat ;
    private double carb;
    private double protein ;
    private LocalDateTime recordDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;

    public Integer getId() {
        return id;
    }

    public double getFat() {
        return fat;
    }

    public double getCarb() {
        return carb;
    }

    public double getProtein() {
        return protein;
    }

    public LocalDateTime getRecordDate() {
        return recordDate;
    }

    public Pet getPet() {
        return pet;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public void setCarb(double carb) {
        this.carb = carb;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public void setRecordDate(LocalDateTime recordDate) {
        this.recordDate = recordDate;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
