package com.example.PetgoraBackend.repository;

import com.example.PetgoraBackend.entity.VisitRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitRecordRepo extends JpaRepository<VisitRecord, Integer> {
    List<VisitRecord> findAllByHealthPassportId(Integer healthPassportId);
    List<VisitRecord> findAllByHealthPassportIdOrderByDateDesc(Integer healthPassportId);
    List<VisitRecord> findAllByHealthPassportIdAndVisitTypeContainingIgnoreCase(Integer healthPassportId, String visitType);

}
