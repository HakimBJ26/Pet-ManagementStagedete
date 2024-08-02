package com.example.PetgoraBackend.repository.petData;



import com.example.PetgoraBackend.entity.petData.Overview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OverviewRepository extends JpaRepository<Overview, Integer> {
    Optional<Overview> findByPetId(Integer petId);
    void deleteByPetId(Integer petId);

}

