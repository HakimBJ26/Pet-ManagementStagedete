package com.example.PetgoraBackend.controller;

import com.example.PetgoraBackend.dto.MedicalRecordDto;
import com.example.PetgoraBackend.dto.VisitRecordDto;
import com.example.PetgoraBackend.service.IMedicalRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical_records")
public class MedicalRecordController {

    private final IMedicalRecordService medicalRecordService;

    public MedicalRecordController(IMedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping("/{healthPassportId}")
    public MedicalRecordDto createMedicalRecord(@PathVariable Integer healthPassportId, @RequestBody MedicalRecordDto medicalRecordDto) {
        return medicalRecordService.createMedicalRecord(healthPassportId, medicalRecordDto);
    }

    @PutMapping("/{id}")
    public MedicalRecordDto updateMedicalRecord(@PathVariable Integer id, @RequestBody MedicalRecordDto medicalRecordDto) {
        return medicalRecordService.updateMedicalRecord(id, medicalRecordDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMedicalRecord(@PathVariable Integer id) {
        medicalRecordService.deleteMedicalRecord(id);
    }

    @GetMapping("/{id}")
    public MedicalRecordDto getMedicalRecordById(@PathVariable Integer id) {
        return medicalRecordService.getMedicalRecordById(id);
    }

    @GetMapping("/healthPassport/{healthPassportId}")
    public List<MedicalRecordDto> getAllMedicalRecordsByHealthPassportId(@PathVariable Integer healthPassportId) {
        return medicalRecordService.getAllMedicalRecordsByHealthPassportId(healthPassportId);
    }


    @GetMapping("/health_passport/{healthPassportId}/sorted")
    public ResponseEntity<List<MedicalRecordDto>> getAllMedicalRecordsByHealthPassportIdSortedByDateDesc(@PathVariable Integer healthPassportId) {
        List<MedicalRecordDto> medicalRecordDtos = medicalRecordService.getAllMedicalRecordsByHealthPassportIdSortedByDateDesc(healthPassportId);
        return ResponseEntity.ok(medicalRecordDtos);
    }



    @GetMapping("/health_passport/{healthPassportId}/search")
    public ResponseEntity<List<MedicalRecordDto>> getAllMedicalRecordsByHealthPassportIdAndVisitType(@PathVariable Integer healthPassportId, @RequestParam String recordType) {
        List<MedicalRecordDto> medicalRecordDtos = medicalRecordService.getAllMedicalRecordsByHealthPassportIdAndRecordType(healthPassportId, recordType);
        return ResponseEntity.ok(medicalRecordDtos);
    }

}
