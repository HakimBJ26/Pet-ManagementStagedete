package com.example.PetgoraBackend.entity;

public record UserDto(
        String name,
        String city,
        String role,
        String email
) {
}
