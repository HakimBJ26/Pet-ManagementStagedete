package com.example.PetgoraBackend.repository.petData;


import com.example.PetgoraBackend.entity.petData.VitalSigns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VitalSignsRepository extends JpaRepository<VitalSigns, Integer> {
    void deleteByPetId(Integer petId);
    VitalSigns findByPetId(Integer petId);

}
