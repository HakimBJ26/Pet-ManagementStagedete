package com.example.PetgoraBackend.mapper.alerts;



import com.example.PetgoraBackend.dto.alerts.HealthAlertDTO;
import com.example.PetgoraBackend.entity.alerts.HealthAlert;
import com.example.PetgoraBackend.entity.Pet;
import org.springframework.stereotype.Component;

@Component
public class HealthAlertMapper {

    public HealthAlertDTO toDTO(HealthAlert healthAlert) {
        HealthAlertDTO dto = new HealthAlertDTO();
        dto.setId(healthAlert.getId());
        dto.setTitle(healthAlert.getTitle());
        dto.setSeverity(healthAlert.getSeverity());
        dto.setAction(healthAlert.getAction());
        dto.setPetId(healthAlert.getPet().getId());
        return dto;
    }

    public HealthAlert toEntity(HealthAlertDTO dto, Pet pet) {
        HealthAlert healthAlert = new HealthAlert();
        healthAlert.setId(dto.getId());
        healthAlert.setTitle(dto.getTitle());
        healthAlert.setSeverity(dto.getSeverity());
        healthAlert.setAction(dto.getAction());
        healthAlert.setPet(pet);
        return healthAlert;
    }
}
