package com.example.PetgoraBackend.controller;

import com.example.PetgoraBackend.dto.VaccineRecordDto;
import com.example.PetgoraBackend.dto.VisitRecordDto;
import com.example.PetgoraBackend.service.IVaccineRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaccine_records")
public class VaccineRecordController {

    private final IVaccineRecordService vaccineRecordService;

    public VaccineRecordController(IVaccineRecordService vaccineRecordService) {
        this.vaccineRecordService = vaccineRecordService;
    }

    @PostMapping("/health_passport/{healthPassportId}")
    public ResponseEntity<VaccineRecordDto> createVaccineRecord(@PathVariable Integer healthPassportId, @RequestBody VaccineRecordDto vaccineRecordDto) {
        VaccineRecordDto createdVaccineRecord = vaccineRecordService.createVaccineRecord(healthPassportId, vaccineRecordDto);
        return ResponseEntity.ok(createdVaccineRecord);
    }

    @PutMapping("/{id}")
    public VaccineRecordDto updateVisitRecord(@PathVariable Integer id, @RequestBody VaccineRecordDto vaccineRecordDto) {
        return vaccineRecordService.updateVaccineRecord(id, vaccineRecordDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVaccineRecord(@PathVariable Integer id) {
        vaccineRecordService.deleteVaccineRecord(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VaccineRecordDto> getVaccineRecordById(@PathVariable Integer id) {
        VaccineRecordDto vaccineRecordDto = vaccineRecordService.getVaccineRecordById(id);
        return ResponseEntity.ok(vaccineRecordDto);
    }

    @GetMapping("/health_passport/{healthPassportId}")
    public ResponseEntity<List<VaccineRecordDto>> getAllVaccineRecordsByHealthPassportId(@PathVariable Integer healthPassportId) {
        List<VaccineRecordDto> vaccineRecordDtos = vaccineRecordService.getAllVaccineRecordsByHealthPassportId(healthPassportId);
        return ResponseEntity.ok(vaccineRecordDtos);
    }

    @GetMapping("/health_passport/{healthPassportId}/sorted")
    public ResponseEntity<List<VaccineRecordDto>> getAllVaccineRecordsByHealthPassportIdSortedByDateDesc(@PathVariable Integer healthPassportId) {
        List<VaccineRecordDto> vaccineRecordDtos = vaccineRecordService.getAllVaccineRecordsByHealthPassportIdSortedByDateDesc(healthPassportId);
        return ResponseEntity.ok(vaccineRecordDtos);
    }

    @GetMapping("/health_passport/{healthPassportId}/search")
    public ResponseEntity<List<VaccineRecordDto>> getAllVaccineRecordsByHealthPassportIdAndVaccineName(@PathVariable Integer healthPassportId, @RequestParam String vaccineName) {
        List<VaccineRecordDto> vaccineRecordDtos = vaccineRecordService.getAllVaccineRecordsByHealthPassportIdAndVaccineName(healthPassportId, vaccineName);
        return ResponseEntity.ok(vaccineRecordDtos);
    }

}
