package com.example.PetgoraBackend.dto;

import com.example.PetgoraBackend.entities.OurUsers;
import com.example.PetgoraBackend.repository.UsersRepo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.aspectj.bridge.Message;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record ReqRes(
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
        String password,
        OurUsers ourUsers,
        List<OurUsers> ourUsersList
) {


    public String getToken() {
        return token;
    }


}