package com.example.PetgoraBackend.mapper;

import com.example.PetgoraBackend.dto.AppointmentDto;
import com.example.PetgoraBackend.entity.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public AppointmentDto toDto(Appointment appointment) {
        AppointmentDto dto = new AppointmentDto();
        dto.setId(appointment.getId());
        dto.setClientId(appointment.getClient().getId());
        dto.setVeterinarianId(appointment.getVeterinarian().getId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setStatus(appointment.getStatus());
        dto.setDescription(appointment.getDescription());
        return dto;
    }

    public Appointment toEntity(AppointmentDto dto) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setStatus(dto.getStatus());
        appointment.setDescription(dto.getDescription());
        return appointment;
    }
}
