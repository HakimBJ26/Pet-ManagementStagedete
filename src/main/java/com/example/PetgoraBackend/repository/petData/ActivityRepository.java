package com.example.PetgoraBackend.repository.petData;

import com.example.PetgoraBackend.entity.petData.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    Activity findByPetId(Integer petId);
}
