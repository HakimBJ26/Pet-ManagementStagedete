package com.example.PetgoraBackend.controller.petData;


import com.example.PetgoraBackend.dto.petData.OverviewDto;
import com.example.PetgoraBackend.service.petData.OverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/overview")
public class OverviewController {

    @Autowired
    private OverviewService overviewService;

    @GetMapping("/pet/{petId}")
    public ResponseEntity<OverviewDto> getOverviewByPetId(@PathVariable Integer petId) {
        OverviewDto overviewDto = overviewService.getOverviewByPetId(petId);
        return new ResponseEntity<>(overviewDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OverviewDto> saveOverview(@RequestBody OverviewDto overviewDTO) {
        OverviewDto savedOverviewDTO = overviewService.saveOverview(overviewDTO);
        return new ResponseEntity<>(savedOverviewDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OverviewDto> updateOverview(@PathVariable Integer id, @RequestBody OverviewDto overviewDTO) {
        OverviewDto updatedOverviewDTO = overviewService.updateOverview(id, overviewDTO);
        return new ResponseEntity<>(updatedOverviewDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOverview(@PathVariable Integer id) {
        overviewService.deleteOverview(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/{id}/activity")
    public ResponseEntity<OverviewDto> deleteRecentActivity(@PathVariable Integer id, @RequestParam String activity) {
        OverviewDto updatedOverview = overviewService.deleteRecentActivity(id, activity);
        return ResponseEntity.ok(updatedOverview);
    }
}
