package com.example.PetgoraBackend.dto.petData;

public class PetGoalDto {
    private Integer id;
    private Integer petId;  // Only include petId
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

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
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
