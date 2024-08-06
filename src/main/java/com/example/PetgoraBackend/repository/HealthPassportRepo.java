package com.example.PetgoraBackend.repository;

import com.example.PetgoraBackend.entity.HealthPassport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthPassportRepo extends JpaRepository<HealthPassport, Integer> {
    HealthPassport findByPet_Id(Integer petId);
}
