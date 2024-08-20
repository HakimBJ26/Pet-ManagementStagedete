package com.example.PetgoraBackend.repository.alerts;

import com.example.PetgoraBackend.entity.alerts.NotificationTimestamps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTimestampsRepository extends JpaRepository<NotificationTimestamps, Integer> {
    NotificationTimestamps findByPetId(Integer petId);
}
