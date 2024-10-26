package com.example.PetgoraBackend.dto;

import java.time.LocalDate;

public class PetsDTO {
    private Integer id;
    private String name;
    private String breed;
    private Integer age;
    private String imageUrl;
    private String blockchainCert;



    // Getter and Setter for blockchainCert
    public String getBlockchainCert() {
        return blockchainCert;
    }

    public void setBlockchainCert(String blockchainCert) {
        this.blockchainCert = blockchainCert;
    }

    // Constructor, getters, and setters
    public PetsDTO(Integer id, String name, String breed, Integer age, String imageUrl,String blockchainCert) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.imageUrl = imageUrl;
        this.blockchainCert=blockchainCert;
    }

    // Getters and Setters for all fields
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
