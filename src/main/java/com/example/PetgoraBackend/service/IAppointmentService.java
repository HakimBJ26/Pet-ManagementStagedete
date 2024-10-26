package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.dto.AppointmentDto;

import java.util.List;

public interface IAppointmentService {
    AppointmentDto createAppointment(AppointmentDto appointmentDto);
    AppointmentDto updateAppointmentStatus(Integer appointmentId, String status);
    List<AppointmentDto> getAppointmentsByVeterinarianId(Integer veterinarianId);
    List<AppointmentDto> getAppointmentsByClientId(Integer clientId);

    AppointmentDto approveAppointment(Integer appointmentId);
}
