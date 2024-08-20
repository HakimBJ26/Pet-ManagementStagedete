package com.example.PetgoraBackend.dto;


public record PetAccessoryDto(
          Long id
        , String name
        , Double price
        , String imageUrl
) {
}
