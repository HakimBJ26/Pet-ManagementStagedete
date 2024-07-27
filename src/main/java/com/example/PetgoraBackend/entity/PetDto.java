package com.example.PetgoraBackend.entity;

public record PetDto(
        Integer id,
        String name,
        String breed,
        Integer age,
        Integer ownerId // Reference to the owner's ID (User)
) {
}
