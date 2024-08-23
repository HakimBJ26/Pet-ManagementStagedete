package com.example.PetgoraBackend.dto.activity;


import java.time.LocalDate;

public class ActivityPropositionDTO {

    private Long id;
    private String name;
    private int duration;
    private String frequency;
    private LocalDate day;


    // Constructors, Getters, and Setters
    public ActivityPropositionDTO() {}

    public ActivityPropositionDTO(Long id, String name, int duration, String frequency, LocalDate day) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.frequency = frequency;
        this.day = day;

    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }

    public LocalDate getDay() { return day; }
    public void setDay(LocalDate day) { this.day = day; }
}
