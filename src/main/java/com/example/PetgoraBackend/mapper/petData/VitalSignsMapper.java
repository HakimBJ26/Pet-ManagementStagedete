package com.example.PetgoraBackend.mapper.petData;


import com.example.PetgoraBackend.dto.petData.VitalSignsDto;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.entity.petData.VitalSigns;
import org.springframework.stereotype.Component;

@Component
public class VitalSignsMapper {

    public VitalSignsDto toDTO(VitalSigns vitalSigns) {
        VitalSignsDto dto = new VitalSignsDto();
        dto.setId(vitalSigns.getId());
        dto.setHeartRate(vitalSigns.getHeartRate());
        dto.setTemperature(vitalSigns.getTemperature());
        dto.setActivityLevel(vitalSigns.getActivityLevel());
        dto.setLastUpdated(vitalSigns.getLastUpdated());
        dto.setPetId(vitalSigns.getPet().getId());
        return dto;
    }

    public VitalSigns toEntity(VitalSignsDto dto, Pet pet) {
        VitalSigns vitalSigns = new VitalSigns();
        vitalSigns.setId(dto.getId());
        vitalSigns.setHeartRate(dto.getHeartRate());
        vitalSigns.setTemperature(dto.getTemperature());
        vitalSigns.setActivityLevel(dto.getActivityLevel());
        vitalSigns.setLastUpdated(dto.getLastUpdated());
        vitalSigns.setPet(pet);
        return vitalSigns;
    }
}
