package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.dto.VaccineRecordDto;
import com.example.PetgoraBackend.dto.VisitRecordDto;
import java.util.List;

public interface IVisitRecordService {
    VisitRecordDto createVisitRecord(Integer healthPassportId, VisitRecordDto visitRecordDto);
    VisitRecordDto updateVisitRecord(Integer id, VisitRecordDto visitRecordDto);
    void deleteVisitRecord(Integer id);
    VisitRecordDto getVisitRecordById(Integer id);
    List<VisitRecordDto> getAllVisitRecordsByHealthPassportId(Integer healthPassportId);
    List<VisitRecordDto> getAllVisitRecordsByHealthPassportIdSortedByDateDesc(Integer healthPassportId);
    List<VisitRecordDto> getAllVisitRecordsByHealthPassportIdAndVisitType(Integer healthPassportId, String visitType);
}
