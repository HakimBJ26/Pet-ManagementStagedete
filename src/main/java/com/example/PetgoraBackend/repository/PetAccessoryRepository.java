package com.example.PetgoraBackend.repository;


import com.example.PetgoraBackend.entity.PetAccessory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetAccessoryRepository extends JpaRepository<PetAccessory, Long> {
    List<PetAccessory> findByNameContainingIgnoreCase(String name);
}
