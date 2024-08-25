package com.example.PetgoraBackend.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class SafeZone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private SafeZoneType type;


    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
    @ElementCollection
    private List<Position> positions;

    public SafeZone() {}
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public List<Position> getPositions() {
        return positions;}

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public SafeZoneType getType() {
        return type;
    }

    public void setType(SafeZoneType type) {
        this.type = type;
    }
}

