package com.example.PetgoraBackend.repository;

import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.entity.Position;
import com.example.PetgoraBackend.entity.SafeZone;
import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepo extends JpaRepository<Pet, Integer> {
    List<Pet> findByOwner_Id(Integer ownerId);


    @Query("SELECT p FROM Pet p WHERE p.requestCertif = true AND p.blockchainCert IS NULL")
    List<Pet> findPetsWithRequestedCertifAndNoBlockchainCert();

    @Query("SELECT p FROM Pet p LEFT JOIN FETCH p.safeZones WHERE p.id = :id")
    Optional<Pet> findByIdWithSafeZones(@Param("id") Integer id);
    @Query("SELECT sz FROM SafeZone sz JOIN sz.pet p WHERE p.id = :petId")
    List<SafeZone> findSafeZonesByPetId(@Param("petId") Integer petId);


}
