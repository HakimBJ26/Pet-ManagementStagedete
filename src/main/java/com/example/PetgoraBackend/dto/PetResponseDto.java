package com.example.PetgoraBackend.dto;

import java.time.LocalDate;

public record PetResponseDto(
        String name,
        String breed,
        Integer age,
        Integer ownerId,
        String imageUrl ,
        String  blockchainCert ,
        boolean requestCertif ,
        LocalDate birthDate

) {

}
