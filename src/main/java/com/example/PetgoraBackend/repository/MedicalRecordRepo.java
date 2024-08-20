package com.example.PetgoraBackend.repository;

import com.example.PetgoraBackend.entity.MedicalRecord;
import com.example.PetgoraBackend.entity.SurgeryRecord;
import com.example.PetgoraBackend.entity.VaccineRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepo extends JpaRepository<MedicalRecord, Integer> {
    List<MedicalRecord> findAllByHealthPassportId(Integer healthPassportId);
    List<MedicalRecord> findAllByHealthPassportIdOrderByDateDesc(Integer healthPassportId);

    List<MedicalRecord> findAllByHealthPassportIdAndRecordTypeContainingIgnoreCase(Integer healthPassportId, String recordType);
}
