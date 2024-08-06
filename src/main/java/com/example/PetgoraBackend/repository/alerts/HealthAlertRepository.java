package com.example.PetgoraBackend.repository.alerts;


import com.example.PetgoraBackend.entity.alerts.HealthAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthAlertRepository extends JpaRepository<HealthAlert, Integer> {
    void deleteByPetId(Integer petId);

}
