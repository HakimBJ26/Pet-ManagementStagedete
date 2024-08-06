package com.example.PetgoraBackend.controller;

import com.example.PetgoraBackend.dto.VaccineRecordDto;
import com.example.PetgoraBackend.dto.VisitRecordDto;
import com.example.PetgoraBackend.service.IVisitRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visit_records")
public class VisitRecordController {

    private final IVisitRecordService visitRecordService;

    public VisitRecordController(IVisitRecordService visitRecordService) {
        this.visitRecordService = visitRecordService;
    }

    @PostMapping("/{healthPassportId}")
    public VisitRecordDto createVisitRecord(@PathVariable Integer healthPassportId, @RequestBody VisitRecordDto visitRecordDto) {
        return visitRecordService.createVisitRecord(healthPassportId, visitRecordDto);
    }

    @PutMapping("/{id}")
    public VisitRecordDto updateVisitRecord(@PathVariable Integer id, @RequestBody VisitRecordDto visitRecordDto) {
        return visitRecordService.updateVisitRecord(id, visitRecordDto);
    }

    @DeleteMapping("/{id}")
    public void deleteVisitRecord(@PathVariable Integer id) {
        visitRecordService.deleteVisitRecord(id);
    }

    @GetMapping("/{id}")
    public VisitRecordDto getVisitRecordById(@PathVariable Integer id) {
        return visitRecordService.getVisitRecordById(id);
    }

    @GetMapping("/healthPassport/{healthPassportId}")
    public List<VisitRecordDto> getAllVisitRecordsByHealthPassportId(@PathVariable Integer healthPassportId) {
        return visitRecordService.getAllVisitRecordsByHealthPassportId(healthPassportId);
    }

    @GetMapping("/health_passport/{healthPassportId}/sorted")
    public ResponseEntity<List<VisitRecordDto>> getAllVisitRecordsByHealthPassportIdSortedByDateDesc(@PathVariable Integer healthPassportId) {
        List<VisitRecordDto> visitRecordDtos = visitRecordService.getAllVisitRecordsByHealthPassportIdSortedByDateDesc(healthPassportId);
        return ResponseEntity.ok(visitRecordDtos);
    }



    @GetMapping("/health_passport/{healthPassportId}/search")
    public ResponseEntity<List<VisitRecordDto>> getAllVisitRecordsByHealthPassportIdAndVisitType(@PathVariable Integer healthPassportId, @RequestParam String visitType) {
        List<VisitRecordDto> visitRecordDtos = visitRecordService.getAllVisitRecordsByHealthPassportIdAndVisitType(healthPassportId, visitType);
        return ResponseEntity.ok(visitRecordDtos);
    }


}
