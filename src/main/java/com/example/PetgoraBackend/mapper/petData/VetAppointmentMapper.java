package com.example.PetgoraBackend.mapper.petData;

import com.example.PetgoraBackend.dto.petData.VetAppointmentDTO;
import com.example.PetgoraBackend.entity.petData.VetAppointment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VetAppointmentMapper {


    VetAppointment toEntity(VetAppointmentDTO dto);

    VetAppointmentDTO toDto(VetAppointment entity);
}
