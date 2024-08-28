package com.example.PetgoraBackend.entity.Convert;

import com.example.PetgoraBackend.dto.PositionPetDto;
import com.example.PetgoraBackend.entity.LocationData;

public class ConversionUtils {

    // Convert LocationData entity to PositionPetDto
    public static PositionPetDto toPositionPetDto(LocationData locationData) {
        if (locationData == null) {
            return null;
        }
        return new PositionPetDto(locationData.getLatitude(), locationData.getLongitude());
    }

    // Convert PositionPetDto to LocationData entity
    public static LocationData toLocationData(PositionPetDto positionPetDto, Integer petId) {
        if (positionPetDto == null) {
            return null;
        }
        LocationData locationData = new LocationData();
        locationData.setPetId(petId);
        locationData.setLatitude(positionPetDto.lat());
        locationData.setLongitude(positionPetDto.lng());
        return locationData;
    }
}
