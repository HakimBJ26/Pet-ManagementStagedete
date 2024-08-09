package com.example.PetgoraBackend.dto.petData;


import lombok.Data;

@Data
public class VetAppointmentComparisonDTO {

    private VetAppointmentDTO lastAppointment;
    private String weightComparison;
    private String bodyComparison;
    private String tailComparison;
    private String chestComparison;
}
