package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.dto.MedicalRecordDto;
import com.example.PetgoraBackend.dto.VisitRecordDto;

import java.util.List;

public interface IMedicalRecordService {
    MedicalRecordDto createMedicalRecord(Integer healthPassportId, MedicalRecordDto medicalRecordDto);
    MedicalRecordDto updateMedicalRecord(Integer id, MedicalRecordDto medicalRecordDto);
    void deleteMedicalRecord(Integer id);
    MedicalRecordDto getMedicalRecordById(Integer id);
    List<MedicalRecordDto> getAllMedicalRecordsByHealthPassportId(Integer healthPassportId);
    List<MedicalRecordDto> getAllMedicalRecordsByHealthPassportIdSortedByDateDesc(Integer healthPassportId);
    List<MedicalRecordDto> getAllMedicalRecordsByHealthPassportIdAndRecordType(Integer healthPassportId, String recordType);
}
