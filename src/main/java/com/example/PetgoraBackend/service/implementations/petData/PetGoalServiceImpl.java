package com.example.PetgoraBackend.service.implementations.petData;


import com.example.PetgoraBackend.entity.petData.PetGoal;
import com.example.PetgoraBackend.repository.petData.PetGoalRepository;
import com.example.PetgoraBackend.service.petData.PetGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetGoalServiceImpl implements PetGoalService {

    @Autowired
    private PetGoalRepository petGoalRepository;

    @Override
    public PetGoal createPetGoal(PetGoal petGoal) {
        return petGoalRepository.save(petGoal);
    }

    @Override
    public Optional<PetGoal> getPetGoalById(Integer id) {
        return petGoalRepository.findById(id);
    }

    @Override
    public List<PetGoal> getAllPetGoals() {
        return petGoalRepository.findAll();
    }

    @Override
    public PetGoal updatePetGoal(Integer id, PetGoal petGoal) {
        if (!petGoalRepository.existsById(id)) {
            throw new RuntimeException("PetGoal with id " + id + " does not exist");
        }
        petGoal.setId(id);
        return petGoalRepository.save(petGoal);
    }

    @Override
    public void deletePetGoal(Integer id) {
        if (!petGoalRepository.existsById(id)) {
            throw new RuntimeException("PetGoal with id " + id + " does not exist");
        }
        petGoalRepository.deleteById(id);
    }
}
