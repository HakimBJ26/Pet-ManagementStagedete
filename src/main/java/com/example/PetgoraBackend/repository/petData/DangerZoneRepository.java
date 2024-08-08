package com.example.PetgoraBackend.repository.petData;

import com.example.PetgoraBackend.entity.DangerZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DangerZoneRepository extends JpaRepository<DangerZone, Long> {
}
