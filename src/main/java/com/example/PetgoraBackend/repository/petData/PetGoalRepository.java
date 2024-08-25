package com.example.PetgoraBackend.repository.petData;


import com.example.PetgoraBackend.entity.petData.PetGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetGoalRepository extends JpaRepository<PetGoal, Integer> {
    Optional<PetGoal> findByPetId(Integer petId);

}
