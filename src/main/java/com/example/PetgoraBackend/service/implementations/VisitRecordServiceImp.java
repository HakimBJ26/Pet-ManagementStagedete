package com.example.PetgoraBackend.service.implementations;

import com.example.PetgoraBackend.dto.VaccineRecordDto;
import com.example.PetgoraBackend.dto.VisitRecordDto;
import com.example.PetgoraBackend.entity.HealthPassport;
import com.example.PetgoraBackend.entity.VaccineRecord;
import com.example.PetgoraBackend.entity.VisitRecord;
import com.example.PetgoraBackend.mapper.VaccineRecordMapper;
import com.example.PetgoraBackend.mapper.VisitRecordMapper;
import com.example.PetgoraBackend.repository.HealthPassportRepo;
import com.example.PetgoraBackend.repository.VisitRecordRepo;
import com.example.PetgoraBackend.service.IVisitRecordService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VisitRecordServiceImp implements IVisitRecordService {

    private final VisitRecordRepo visitRecordRepo;
    private final HealthPassportRepo healthPassportRepo;

    public VisitRecordServiceImp(VisitRecordRepo visitRecordRepo, HealthPassportRepo healthPassportRepo) {
        this.visitRecordRepo = visitRecordRepo;
        this.healthPassportRepo = healthPassportRepo;
    }

    @Override
    public VisitRecordDto createVisitRecord(Integer healthPassportId, VisitRecordDto visitRecordDto) {
        HealthPassport healthPassport = healthPassportRepo.findById(healthPassportId)
                .orElseThrow(() -> new EntityNotFoundException("Health Passport not found for ID " + healthPassportId));
        VisitRecord visitRecord = VisitRecordMapper.INSTANCE.toVisitRecord(visitRecordDto);
        visitRecord.setHealthPassport(healthPassport);
        visitRecord = visitRecordRepo.save(visitRecord);
        return VisitRecordMapper.INSTANCE.toVisitRecordDto(visitRecord);
    }

    @Override
    public VisitRecordDto updateVisitRecord(Integer id, VisitRecordDto visitRecordDto) {
        VisitRecord visitRecord = visitRecordRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visit Record not found for ID " + id));
        VisitRecordMapper.INSTANCE.updateVisitRecordFromDto(visitRecordDto, visitRecord);
        visitRecord = visitRecordRepo.save(visitRecord);
        return VisitRecordMapper.INSTANCE.toVisitRecordDto(visitRecord);
    }

    @Override
    public void deleteVisitRecord(Integer id) {
        if (!visitRecordRepo.existsById(id)) {
            throw new EntityNotFoundException("Visit Record not found for ID " + id);
        }
        visitRecordRepo.deleteById(id);
    }

    @Override
    public VisitRecordDto getVisitRecordById(Integer id) {
        VisitRecord visitRecord = visitRecordRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visit Record not found for ID " + id));
        return VisitRecordMapper.INSTANCE.toVisitRecordDto(visitRecord);
    }

    @Override
    public List<VisitRecordDto> getAllVisitRecordsByHealthPassportId(Integer healthPassportId) {
        List<VisitRecord> visitRecords = visitRecordRepo.findAllByHealthPassportId(healthPassportId);
        return visitRecords.stream()
                .map(VisitRecordMapper.INSTANCE::toVisitRecordDto)
                .collect(Collectors.toList());
    }



    @Override
    public List<VisitRecordDto> getAllVisitRecordsByHealthPassportIdSortedByDateDesc(Integer healthPassportId) {
        List<VisitRecord> visitRecords = visitRecordRepo.findAllByHealthPassportIdOrderByDateDesc(healthPassportId);
        return visitRecords.stream()
                .map(VisitRecordMapper.INSTANCE::toVisitRecordDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<VisitRecordDto> getAllVisitRecordsByHealthPassportIdAndVisitType(Integer healthPassportId, String visitType) {
        return visitRecordRepo.findAllByHealthPassportIdAndVisitTypeContainingIgnoreCase(healthPassportId, visitType).stream()
                .map(VisitRecordMapper.INSTANCE::toVisitRecordDto)
                .collect(Collectors.toList());
    }



}
