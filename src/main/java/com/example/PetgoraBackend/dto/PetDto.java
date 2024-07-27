package com.example.PetgoraBackend.dto;

public record PetDto(
        String name,
        String breed,
        Integer age,
        Integer ownerId // Reference to the owner's ID (User)
) {
}
