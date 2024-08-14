package com.example.PetgoraBackend.controller.petData;

import com.example.PetgoraBackend.dto.petData.VetAppointmentComparisonDTO;
import com.example.PetgoraBackend.dto.petData.VetAppointmentDTO;
import com.example.PetgoraBackend.service.petData.VetAppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vet-appointments")
public class VetAppointmentController {

    private final VetAppointmentService vetAppointmentService;

    public VetAppointmentController(VetAppointmentService vetAppointmentService) {
        this.vetAppointmentService = vetAppointmentService;
    }

    @PostMapping("/{petId}")
    @PreAuthorize("hasRole('VETERINARIAN')")
    public ResponseEntity<VetAppointmentDTO> createVetAppointment(@RequestBody VetAppointmentDTO vetAppointmentDTO, @PathVariable Integer petId) {
        VetAppointmentDTO createdAppointment = vetAppointmentService.createVetAppointment(vetAppointmentDTO, petId);
        return ResponseEntity.ok(createdAppointment);
    }

    @GetMapping("/{petId}/compare")
    public ResponseEntity<VetAppointmentComparisonDTO> getLastTwoVetAppointments(@PathVariable Integer petId) {
        VetAppointmentComparisonDTO comparisonDTO = vetAppointmentService.getLastTwoVetAppointments(petId);
        return ResponseEntity.ok(comparisonDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('VETERINARIAN')")
    public ResponseEntity<Void> deleteVetAppointment(@PathVariable Integer id) {
        vetAppointmentService.deleteVetAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('VETERINARIAN')")
    public ResponseEntity<VetAppointmentDTO> updateVetAppointment(@PathVariable Integer id, @RequestBody VetAppointmentDTO vetAppointmentDTO) {
        VetAppointmentDTO updatedAppointment = vetAppointmentService.updateVetAppointment(id, vetAppointmentDTO);
        return ResponseEntity.ok(updatedAppointment);
    }
}
