package com.example.PetgoraBackend.repository.petData;

import com.example.PetgoraBackend.entity.SafeZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface SafeZoneRepository extends JpaRepository<SafeZone, Long> {
}
