package com.example.PetgoraBackend.dto;

public record CurrentUserPetResponseDto(

        Integer id,
        String name,
        String breed,
        Integer age,

        byte[] image

) {
}
