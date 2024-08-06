package com.example.PetgoraBackend.mapper.petData;


import com.example.PetgoraBackend.dto.petData.OverviewDto;
import com.example.PetgoraBackend.entity.petData.Overview;
import com.example.PetgoraBackend.entity.Pet;
import org.springframework.stereotype.Component;

@Component
public class OverviewMapper {

    public OverviewDto toDTO(Overview overview) {
        OverviewDto dto = new OverviewDto();
        dto.setId(overview.getId());
        dto.setRecentActivity(overview.getRecentActivity());
        dto.setNextCheckUp(overview.getNextCheckUp());
        dto.setHealthStatus(overview.getHealthStatus().name());
        dto.setPetId(overview.getPet().getId());
        return dto;
    }

    public Overview toEntity(OverviewDto dto, Pet pet) {
        Overview overview = new Overview();
        overview.setId(dto.getId());
        overview.setRecentActivity(dto.getRecentActivity());
        overview.setNextCheckUp(dto.getNextCheckUp());
        overview.setHealthStatus(Overview.HealthStatus.valueOf(dto.getHealthStatus()));
        overview.setPet(pet);
        return overview;
    }
}
