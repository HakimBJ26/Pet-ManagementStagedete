package com.example.PetgoraBackend.repository;

import com.example.PetgoraBackend.entity.VaccineRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccineRecordRepo extends JpaRepository<VaccineRecord, Integer> {
    List<VaccineRecord> findAllByHealthPassportId(Integer healthPassportId);
    List<VaccineRecord> findAllByHealthPassportIdOrderByDateDesc(Integer healthPassportId);
    List<VaccineRecord> findAllByHealthPassportIdAndVaccineNameContainingIgnoreCase(Integer healthPassportId, String vaccineName);

}
