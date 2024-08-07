package com.example.PetgoraBackend.repository;

import com.example.PetgoraBackend.entity.HealthReminder;
import com.example.PetgoraBackend.entity.VaccineRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HealthReminderRepo extends JpaRepository<HealthReminder, Integer> {
    List<HealthReminder> findAllByHealthPassportId(Integer healthPassportId);
}
