
package com.example.PetgoraBackend.dto.petData;

import java.time.LocalDate;

public class PetCertifDto {
    private Integer uniqueId;
    private String petName;
    private String breed;
    private LocalDate birthDate;
    private   Integer ownerId ;
    private String imageUrl ;



    public PetCertifDto(Integer uniqueId, String petName, String breed, LocalDate birthDate, Integer ownerId, String imageUrl) {
        this.uniqueId = uniqueId;
        this.petName = petName;
        this.breed = breed;
        this.birthDate = birthDate;
        this.ownerId=ownerId;
        this.imageUrl=imageUrl;
    }

    public Integer getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
