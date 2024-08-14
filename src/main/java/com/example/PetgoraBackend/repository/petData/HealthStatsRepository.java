package com.example.PetgoraBackend.repository.petData;

import com.example.PetgoraBackend.entity.petData.HealthStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HealthStatsRepository extends JpaRepository<HealthStats, Integer> {

    Page<HealthStats> findByPetId(Integer petId, Pageable pageable);

}
