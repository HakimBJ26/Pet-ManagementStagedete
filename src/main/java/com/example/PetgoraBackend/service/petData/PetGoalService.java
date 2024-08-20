package com.example.PetgoraBackend.service.petData;

import com.example.PetgoraBackend.entity.petData.PetGoal;
import java.util.List;
import java.util.Optional;

public interface PetGoalService {
    PetGoal createPetGoal(PetGoal petGoal);
    Optional<PetGoal> getPetGoalById(Integer id);
    List<PetGoal> getAllPetGoals();
    PetGoal updatePetGoal(Integer id, PetGoal petGoal);
    void deletePetGoal(Integer id);
}
