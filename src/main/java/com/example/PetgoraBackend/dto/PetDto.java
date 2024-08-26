package com.example.PetgoraBackend.dto;


import java.util.List;


public record PetDto(
        String name,
        String breed,
        Integer age,

         List<SafeZoneDto> safeZones,
        List<DangerZoneDto> dangerZones

) {
}
