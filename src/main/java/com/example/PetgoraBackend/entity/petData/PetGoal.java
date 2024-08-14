package com.example.PetgoraBackend.entity.petData;


import com.example.PetgoraBackend.entity.Pet;
import jakarta.persistence.*;

@Entity
@Table(name = "pet_goal")
public class PetGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pet_id", referencedColumnName = "id", unique = true)
    private Pet pet;

    private Double currentWeight;
    private String healthGoal;
    private String dailyExerciseRoutine;

    // Getters and Setters

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

    public Double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public String getHealthGoal() {
        return healthGoal;
    }

    public void setHealthGoal(String healthGoal) {
        this.healthGoal = healthGoal;
    }

    public String getDailyExerciseRoutine() {
        return dailyExerciseRoutine;
    }

    public void setDailyExerciseRoutine(String dailyExerciseRoutine) {
        this.dailyExerciseRoutine = dailyExerciseRoutine;
    }
}
