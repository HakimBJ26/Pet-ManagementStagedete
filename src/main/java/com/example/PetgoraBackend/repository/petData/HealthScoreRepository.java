package com.example.PetgoraBackend.repository.petData;

import com.example.PetgoraBackend.entity.petData.HealthScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthScoreRepository extends JpaRepository<HealthScore, Long> {
    HealthScore findByPetId(Integer petId);
}
