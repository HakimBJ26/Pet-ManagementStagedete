package com.example.PetgoraBackend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDto {
    private Integer id;
    private Integer clientId;
    private Integer veterinarianId;
    private LocalDateTime appointmentDate;
    private String status;
    private String description;
}
