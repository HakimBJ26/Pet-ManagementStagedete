package com.example.PetgoraBackend.repository;

import com.example.PetgoraBackend.entity.SurgeryRecord;
import com.example.PetgoraBackend.entity.VisitRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurgeryRecordRepo extends JpaRepository<SurgeryRecord, Integer> {
    List<SurgeryRecord> findAllByHealthPassportId(Integer healthPassportId);

    List<SurgeryRecord> findAllByHealthPassportIdOrderByDateDesc(Integer healthPassportId);

    List<SurgeryRecord> findAllByHealthPassportIdAndSurgeryTypeContainingIgnoreCase(Integer healthPassportId, String surgeryType);
}