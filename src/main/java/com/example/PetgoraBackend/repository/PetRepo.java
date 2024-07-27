package com.example.PetgoraBackend.repository;

import com.example.PetgoraBackend.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepo extends JpaRepository<Pet, Integer> {
    List<Pet> findByOwner_Id(Integer ownerId);
}
