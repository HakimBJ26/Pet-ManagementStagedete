package com.example.PetgoraBackend.dto;

public class PetsDTO {
    private Integer id;
    private String name;
    private String breed;
    private Integer age;
    private String imageUrl;

    // Constructor, getters, and setters
    public PetsDTO(Integer id, String name, String breed, Integer age, String imageUrl) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.imageUrl = imageUrl; // Set the imageUrl here
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
