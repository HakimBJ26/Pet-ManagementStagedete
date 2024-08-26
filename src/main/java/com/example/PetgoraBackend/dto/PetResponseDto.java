package com.example.PetgoraBackend.dto;

public record PetResponseDto(
        String name,
        String breed,
        Integer age,
        Integer ownerId,
        String imageUrl

) {

}
