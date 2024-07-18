package com.example.PetgoraBackend.entity;

public record UserDto(
        Integer id,
        String name,
        String city,
        String role,

        String email,
        String password


) {

}
