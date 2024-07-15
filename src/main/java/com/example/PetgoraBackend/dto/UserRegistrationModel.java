package com.example.PetgoraBackend.dto;

public record UserRegistrationModel (
        int statusCode,
        String error,
        String message,
        String token,
        String refreshToken,
        String expirationTime,
        String name,
        String city,
        String role,
        String email,
        String password
//        OurUsers ourUsers,
//        List<OurUsers> ourUsersList

){
}
