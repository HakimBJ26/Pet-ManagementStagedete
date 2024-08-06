package com.example.PetgoraBackend.service.implementations;

import com.example.PetgoraBackend.dto.MedicalRecordDto;
import com.example.PetgoraBackend.dto.VisitRecordDto;
import com.example.PetgoraBackend.entity.HealthPassport;
import com.example.PetgoraBackend.entity.MedicalRecord;
import com.example.PetgoraBackend.entity.VisitRecord;
import com.example.PetgoraBackend.mapper.MedicalRecordMapper;
import com.example.PetgoraBackend.mapper.VisitRecordMapper;
import com.example.PetgoraBackend.repository.HealthPassportRepo;
import com.example.PetgoraBackend.repository.MedicalRecordRepo;
import com.example.PetgoraBackend.service.IMedicalRecordService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedicalRecordServiceImp implements IMedicalRecordService {

    private final MedicalRecordRepo medicalRecordRepo;
    private final HealthPassportRepo healthPassportRepo;

    public MedicalRecordServiceImp(MedicalRecordRepo medicalRecordRepo, HealthPassportRepo healthPassportRepo) {
        this.medicalRecordRepo = medicalRecordRepo;
        this.healthPassportRepo = healthPassportRepo;
    }

    @Override
    public MedicalRecordDto createMedicalRecord(Integer healthPassportId, MedicalRecordDto medicalRecordDto) {
        HealthPassport healthPassport = healthPassportRepo.findById(healthPassportId)
                .orElseThrow(() -> new EntityNotFoundException("Health Passport not found for ID " + healthPassportId));
        MedicalRecord medicalRecord = MedicalRecordMapper.INSTANCE.toMedicalRecord(medicalRecordDto);
        medicalRecord.setHealthPassport(healthPassport);
        medicalRecord = medicalRecordRepo.save(medicalRecord);
        return MedicalRecordMapper.INSTANCE.toMedicalRecordDto(medicalRecord);
    }

    @Override
    public MedicalRecordDto updateMedicalRecord(Integer id, MedicalRecordDto medicalRecordDto) {
        MedicalRecord medicalRecord = medicalRecordRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medical Record not found for ID " + id));
        MedicalRecordMapper.INSTANCE.updateMedicalRecordFromDto(medicalRecordDto, medicalRecord);
        medicalRecord = medicalRecordRepo.save(medicalRecord);
        return MedicalRecordMapper.INSTANCE.toMedicalRecordDto(medicalRecord);
    }

    @Override
    public void deleteMedicalRecord(Integer id) {
        if (!medicalRecordRepo.existsById(id)) {
            throw new EntityNotFoundException("Medical Record not found for ID " + id);
        }
        medicalRecordRepo.deleteById(id);
    }

    @Override
    public MedicalRecordDto getMedicalRecordById(Integer id) {
        MedicalRecord medicalRecord = medicalRecordRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medical Record not found for ID " + id));
        return MedicalRecordMapper.INSTANCE.toMedicalRecordDto(medicalRecord);
    }

    @Override
    public List<MedicalRecordDto> getAllMedicalRecordsByHealthPassportId(Integer healthPassportId) {
        List<MedicalRecord> medicalRecords = medicalRecordRepo.findAllByHealthPassportId(healthPassportId);
        return medicalRecords.stream()
                .map(MedicalRecordMapper.INSTANCE::toMedicalRecordDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicalRecordDto> getAllMedicalRecordsByHealthPassportIdSortedByDateDesc(Integer healthPassportId) {
        List<MedicalRecord> medicalRecords = medicalRecordRepo.findAllByHealthPassportIdOrderByDateDesc(healthPassportId);
        return medicalRecords.stream()
                .map(MedicalRecordMapper.INSTANCE::toMedicalRecordDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<MedicalRecordDto> getAllMedicalRecordsByHealthPassportIdAndRecordType(Integer healthPassportId, String recordType) {
        return medicalRecordRepo.findAllByHealthPassportIdAndRecordTypeContainingIgnoreCase(healthPassportId, recordType).stream()
                .map(MedicalRecordMapper.INSTANCE::toMedicalRecordDto)
                .collect(Collectors.toList());
    }
}
