package com.example.PetgoraBackend.service.petData;

import com.example.PetgoraBackend.dto.petData.VetAppointmentComparisonDTO;
import com.example.PetgoraBackend.dto.petData.VetAppointmentDTO;

public interface VetAppointmentService {
    VetAppointmentDTO createVetAppointment(VetAppointmentDTO vetAppointmentDTO, Integer petId);
    VetAppointmentComparisonDTO getLastTwoVetAppointments(Integer petId);
}
