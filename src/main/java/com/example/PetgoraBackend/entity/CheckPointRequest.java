package com.example.PetgoraBackend.entity;

import com.example.PetgoraBackend.dto.PositionPetDto;
import com.example.PetgoraBackend.dto.SafeZoneDto;

import java.util.List;

public class CheckPointRequest {
    public PositionPetDto getPoint() {
        return point;
    }

    public void setPoint(PositionPetDto point) {
        this.point = point;
    }

    public List<SafeZoneDto> getZones() {
        return zones;
    }

    public void setZones(List<SafeZoneDto> zones) {
        this.zones = zones;
    }

    private PositionPetDto point;
    private List<SafeZoneDto> zones;

}
