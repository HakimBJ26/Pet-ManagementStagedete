package com.example.PetgoraBackend.controller;

import com.example.PetgoraBackend.dto.AppointmentDto;
import com.example.PetgoraBackend.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private IAppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody AppointmentDto appointmentDto) {
        AppointmentDto createdAppointment = appointmentService.createAppointment(appointmentDto);
        return ResponseEntity.ok(createdAppointment);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<AppointmentDto> updateAppointmentStatus(
            @PathVariable Integer id,
            @RequestParam String status) {
        AppointmentDto updatedAppointment = appointmentService.updateAppointmentStatus(id, status);
        return ResponseEntity.ok(updatedAppointment);
    }

    @GetMapping("/veterinarian/{veterinarianId}")
    public ResponseEntity<List<AppointmentDto>> getAppointmentsByVeterinarianId(@PathVariable Integer veterinarianId) {
        List<AppointmentDto> appointments = appointmentService.getAppointmentsByVeterinarianId(veterinarianId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<AppointmentDto>> getAppointmentsByClientId(@PathVariable Integer clientId) {
        List<AppointmentDto> appointments = appointmentService.getAppointmentsByClientId(clientId);
        return ResponseEntity.ok(appointments);
    }


    @PostMapping("/{id}/approve")
    public ResponseEntity<AppointmentDto> approveAppointment(@PathVariable Integer id) {
        AppointmentDto approvedAppointment = appointmentService.approveAppointment(id);
        return ResponseEntity.ok(approvedAppointment);
    }
}
