package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.dto.VaccineRecordDto;
import java.util.List;

public interface IVaccineRecordService {
    VaccineRecordDto createVaccineRecord(Integer healthPassportId, VaccineRecordDto vaccineRecordDto);
    VaccineRecordDto updateVaccineRecord(Integer id, VaccineRecordDto vaccineRecordDto);
    void deleteVaccineRecord(Integer id);
    VaccineRecordDto getVaccineRecordById(Integer id);
    List<VaccineRecordDto> getAllVaccineRecordsByHealthPassportId(Integer healthPassportId);
    List<VaccineRecordDto> getAllVaccineRecordsByHealthPassportIdSortedByDateDesc(Integer healthPassportId);
    List<VaccineRecordDto> getAllVaccineRecordsByHealthPassportIdAndVaccineName(Integer healthPassportId, String vaccineName);

}
