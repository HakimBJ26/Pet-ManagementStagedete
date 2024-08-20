package com.example.PetgoraBackend.repository.petData;

import com.example.PetgoraBackend.entity.SafeZone;
import com.example.PetgoraBackend.entity.SafeZoneType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface SafeZoneRepository extends JpaRepository<SafeZone, Long> {
    @Query("SELECT sz FROM SafeZone sz WHERE sz.pet.id = :petId AND sz.type = :type")
    List<SafeZone> findSafeZonesByPetIdAndType(@Param("petId") Integer petId, @Param("type") SafeZoneType type);



}
