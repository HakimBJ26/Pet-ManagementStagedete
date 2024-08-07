package com.example.PetgoraBackend.controller;

import com.example.PetgoraBackend.dto.SurgeryRecordDto;
import com.example.PetgoraBackend.service.ISurgeryRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surgery_records")
public class SurgeryRecordController {

    private final ISurgeryRecordService surgeryRecordService;

    public SurgeryRecordController(ISurgeryRecordService surgeryRecordService) {
        this.surgeryRecordService = surgeryRecordService;
    }

    @PostMapping("/{healthPassportId}")
    public SurgeryRecordDto createSurgeryRecord(@PathVariable Integer healthPassportId, @RequestBody SurgeryRecordDto surgeryRecordDto) {
        return surgeryRecordService.createSurgeryRecord(healthPassportId, surgeryRecordDto);
    }

    @PutMapping("/{id}")
    public SurgeryRecordDto updateSurgeryRecord(@PathVariable Integer id, @RequestBody SurgeryRecordDto surgeryRecordDto) {
        return surgeryRecordService.updateSurgeryRecord(id, surgeryRecordDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSurgeryRecord(@PathVariable Integer id) {
        surgeryRecordService.deleteSurgeryRecord(id);
    }

    @GetMapping("/{id}")
    public SurgeryRecordDto getSurgeryRecordById(@PathVariable Integer id) {
        return surgeryRecordService.getSurgeryRecordById(id);
    }

    @GetMapping("/healthPassport/{healthPassportId}")
    public List<SurgeryRecordDto> getAllSurgeryRecordsByHealthPassportId(@PathVariable Integer healthPassportId) {
        return surgeryRecordService.getAllSurgeryRecordsByHealthPassportId(healthPassportId);
    }

    @GetMapping("/health_passport/{healthPassportId}/sorted")
    public ResponseEntity<List<SurgeryRecordDto>> getAllSurgeryRecordsByHealthPassportIdSortedByDateDesc(@PathVariable Integer healthPassportId) {
        List<SurgeryRecordDto> surgeryRecordDtos = surgeryRecordService.getAllSurgeryRecordsByHealthPassportIdSortedByDateDesc(healthPassportId);
        return ResponseEntity.ok(surgeryRecordDtos);
    }


    @GetMapping("/health_passport/{healthPassportId}/search")
    public ResponseEntity<List<SurgeryRecordDto>> getAllSurgeryRecordsByHealthPassportIdAndSurgeryType(@PathVariable Integer healthPassportId, @RequestParam String surgeryType) {
        List<SurgeryRecordDto> surgeryRecordDtos = surgeryRecordService.getAllSurgeryRecordsByHealthPassportIdAndSurgeryType(healthPassportId, surgeryType);
        return ResponseEntity.ok(surgeryRecordDtos);
    }

}
