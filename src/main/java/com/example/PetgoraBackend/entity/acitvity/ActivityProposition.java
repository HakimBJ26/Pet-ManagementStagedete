package com.example.PetgoraBackend.entity.acitvity;


import com.example.PetgoraBackend.entity.Pet;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class ActivityProposition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int duration; // duration in minutes

    @Column(nullable = false)
    private String frequency;

    @Column(nullable = false)
    private LocalDate day;


    public ActivityProposition() {}

    public ActivityProposition(String name, int duration, String frequency, LocalDate day) {
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
