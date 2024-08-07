package com.example.PetgoraBackend.service.implementations;

import com.example.PetgoraBackend.dto.SurgeryRecordDto;
import com.example.PetgoraBackend.entity.HealthPassport;
import com.example.PetgoraBackend.entity.SurgeryRecord;
import com.example.PetgoraBackend.mapper.SurgeryRecordMapper;
import com.example.PetgoraBackend.repository.HealthPassportRepo;
import com.example.PetgoraBackend.repository.SurgeryRecordRepo;
import com.example.PetgoraBackend.service.ISurgeryRecordService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SurgeryRecordServiceImp implements ISurgeryRecordService {

    private final SurgeryRecordRepo surgeryRecordRepo;
    private final HealthPassportRepo healthPassportRepo;

    public SurgeryRecordServiceImp(SurgeryRecordRepo surgeryRecordRepo, HealthPassportRepo healthPassportRepo) {
        this.surgeryRecordRepo = surgeryRecordRepo;
        this.healthPassportRepo = healthPassportRepo;
    }

    @Override
    public SurgeryRecordDto createSurgeryRecord(Integer healthPassportId, SurgeryRecordDto surgeryRecordDto) {
        HealthPassport healthPassport = healthPassportRepo.findById(healthPassportId)
                .orElseThrow(() -> new EntityNotFoundException("Health Passport not found for ID " + healthPassportId));
        SurgeryRecord surgeryRecord = SurgeryRecordMapper.INSTANCE.toSurgeryRecord(surgeryRecordDto);
        surgeryRecord.setHealthPassport(healthPassport);
        surgeryRecord = surgeryRecordRepo.save(surgeryRecord);
        return SurgeryRecordMapper.INSTANCE.toSurgeryRecordDto(surgeryRecord);
    }

    @Override
    public SurgeryRecordDto updateSurgeryRecord(Integer id, SurgeryRecordDto surgeryRecordDto) {
        SurgeryRecord surgeryRecord = surgeryRecordRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Surgery Record not found for ID " + id));
        SurgeryRecordMapper.INSTANCE.updateSurgeryRecordFromDto(surgeryRecordDto, surgeryRecord);
        surgeryRecord = surgeryRecordRepo.save(surgeryRecord);
        return SurgeryRecordMapper.INSTANCE.toSurgeryRecordDto(surgeryRecord);
    }

    @Override
    public void deleteSurgeryRecord(Integer id) {
        if (!surgeryRecordRepo.existsById(id)) {
            throw new EntityNotFoundException("Surgery Record not found for ID " + id);
        }
        surgeryRecordRepo.deleteById(id);
    }

    @Override
    public SurgeryRecordDto getSurgeryRecordById(Integer id) {
        SurgeryRecord surgeryRecord = surgeryRecordRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Surgery Record not found for ID " + id));
        return SurgeryRecordMapper.INSTANCE.toSurgeryRecordDto(surgeryRecord);
    }

    @Override
    public List<SurgeryRecordDto> getAllSurgeryRecordsByHealthPassportId(Integer healthPassportId) {
        List<SurgeryRecord> surgeryRecords = surgeryRecordRepo.findAllByHealthPassportId(healthPassportId);
        return surgeryRecords.stream()
                .map(SurgeryRecordMapper.INSTANCE::toSurgeryRecordDto)
                .collect(Collectors.toList());
    }



    @Override
    public List<SurgeryRecordDto> getAllSurgeryRecordsByHealthPassportIdSortedByDateDesc(Integer healthPassportId) {
        List<SurgeryRecord> surgeryRecords = surgeryRecordRepo.findAllByHealthPassportIdOrderByDateDesc(healthPassportId);
        return surgeryRecords.stream()
                .map(SurgeryRecordMapper.INSTANCE::toSurgeryRecordDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<SurgeryRecordDto> getAllSurgeryRecordsByHealthPassportIdAndSurgeryType(Integer healthPassportId, String surgeryType) {
        return surgeryRecordRepo.findAllByHealthPassportIdAndSurgeryTypeContainingIgnoreCase(healthPassportId, surgeryType).stream()
                .map(SurgeryRecordMapper.INSTANCE::toSurgeryRecordDto)
                .collect(Collectors.toList());
    }
}
