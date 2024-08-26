package com.example.PetgoraBackend.entity.petData;


import com.example.PetgoraBackend.entity.Pet;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class SleepPattern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    private LocalDate day;
}
