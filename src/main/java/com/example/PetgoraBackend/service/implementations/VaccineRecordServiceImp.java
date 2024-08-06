package com.example.PetgoraBackend.service.implementations;

import com.example.PetgoraBackend.dto.VaccineRecordDto;
import com.example.PetgoraBackend.dto.VisitRecordDto;
import com.example.PetgoraBackend.entity.HealthPassport;
import com.example.PetgoraBackend.entity.VaccineRecord;
import com.example.PetgoraBackend.entity.VisitRecord;
import com.example.PetgoraBackend.mapper.VaccineRecordMapper;
import com.example.PetgoraBackend.mapper.VisitRecordMapper;
import com.example.PetgoraBackend.repository.HealthPassportRepo;
import com.example.PetgoraBackend.repository.VaccineRecordRepo;
import com.example.PetgoraBackend.service.IVaccineRecordService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VaccineRecordServiceImp implements IVaccineRecordService {

    private final VaccineRecordRepo vaccineRecordRepo;
    private final HealthPassportRepo healthPassportRepo;

    public VaccineRecordServiceImp(VaccineRecordRepo vaccineRecordRepo, HealthPassportRepo healthPassportRepo) {
        this.vaccineRecordRepo = vaccineRecordRepo;
        this.healthPassportRepo = healthPassportRepo;
    }

    @Override
    public VaccineRecordDto createVaccineRecord(Integer healthPassportId, VaccineRecordDto vaccineRecordDto) {
        HealthPassport healthPassport = healthPassportRepo.findById(healthPassportId)
                .orElseThrow(() -> new EntityNotFoundException("Health Passport not found for ID " + healthPassportId));
        VaccineRecord vaccineRecord = VaccineRecordMapper.INSTANCE.toVaccineRecord(vaccineRecordDto);
        vaccineRecord.setHealthPassport(healthPassport);
        vaccineRecord = vaccineRecordRepo.save(vaccineRecord);
        return VaccineRecordMapper.INSTANCE.toVaccineRecordDto(vaccineRecord);
    }

    @Override
    public VaccineRecordDto updateVaccineRecord(Integer id, VaccineRecordDto vaccineRecordDto) {
        VaccineRecord vaccineRecord = vaccineRecordRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vaccine Record not found for ID " + id));
        VaccineRecordMapper.INSTANCE.updateVaccineRecordFromDto(vaccineRecordDto, vaccineRecord);
        vaccineRecord = vaccineRecordRepo.save(vaccineRecord);
        return VaccineRecordMapper.INSTANCE.toVaccineRecordDto(vaccineRecord);
    }



    @Override
    public void deleteVaccineRecord(Integer id) {
        if (!vaccineRecordRepo.existsById(id)) {
            throw new EntityNotFoundException("Vaccine Record not found for ID " + id);
        }
        vaccineRecordRepo.deleteById(id);
    }

    @Override
    public VaccineRecordDto getVaccineRecordById(Integer id) {
        VaccineRecord vaccineRecord = vaccineRecordRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vaccine Record not found for ID " + id));
        return VaccineRecordMapper.INSTANCE.toVaccineRecordDto(vaccineRecord);
    }

    @Override
    public List<VaccineRecordDto> getAllVaccineRecordsByHealthPassportId(Integer healthPassportId) {
        List<VaccineRecord> vaccineRecords = vaccineRecordRepo.findAllByHealthPassportId(healthPassportId);
        return vaccineRecords.stream()
                .map(VaccineRecordMapper.INSTANCE::toVaccineRecordDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<VaccineRecordDto> getAllVaccineRecordsByHealthPassportIdSortedByDateDesc(Integer healthPassportId) {
        List<VaccineRecord> vaccineRecords = vaccineRecordRepo.findAllByHealthPassportIdOrderByDateDesc(healthPassportId);
        return vaccineRecords.stream()
                .map(VaccineRecordMapper.INSTANCE::toVaccineRecordDto)
                .collect(Collectors.toList());
    }




    @Override
    public List<VaccineRecordDto> getAllVaccineRecordsByHealthPassportIdAndVaccineName(Integer healthPassportId, String vaccineName) {
        List<VaccineRecord> vaccineRecords = vaccineRecordRepo.findAllByHealthPassportIdAndVaccineNameContainingIgnoreCase(healthPassportId, vaccineName);
        return vaccineRecords.stream()
                .map(VaccineRecordMapper.INSTANCE::toVaccineRecordDto)
                .collect(Collectors.toList());
    }



}
