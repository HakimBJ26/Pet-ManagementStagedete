package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.dto.PetAccessoryDto;
import com.example.PetgoraBackend.entity.PetAccessory;

import java.util.List;
import java.util.Optional;

public interface IPetAccessoryService {
    List<PetAccessoryDto> findAll();
    Optional<PetAccessoryDto> findById(Long id);
    PetAccessoryDto save(PetAccessoryDto petAccessoryDto);
    void deleteById(Long id);
    public List<PetAccessory> findByName(String name);
}
