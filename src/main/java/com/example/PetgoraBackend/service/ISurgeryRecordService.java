package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.dto.SurgeryRecordDto;
import com.example.PetgoraBackend.dto.VisitRecordDto;

import java.util.List;

public interface ISurgeryRecordService {
    SurgeryRecordDto createSurgeryRecord(Integer healthPassportId, SurgeryRecordDto surgeryRecordDto);
    SurgeryRecordDto updateSurgeryRecord(Integer id, SurgeryRecordDto surgeryRecordDto);
    void deleteSurgeryRecord(Integer id);
    SurgeryRecordDto getSurgeryRecordById(Integer id);
    List<SurgeryRecordDto> getAllSurgeryRecordsByHealthPassportId(Integer healthPassportId);
    List<SurgeryRecordDto> getAllSurgeryRecordsByHealthPassportIdSortedByDateDesc(Integer healthPassportId);
    List<SurgeryRecordDto> getAllSurgeryRecordsByHealthPassportIdAndSurgeryType(Integer healthPassportId, String surgeryType);
}
