package com.example.PetgoraBackend.service.implementations;

import com.example.PetgoraBackend.dto.PetAccessoryDto;
import com.example.PetgoraBackend.entity.PetAccessory;
import com.example.PetgoraBackend.repository.PetAccessoryRepository;
import com.example.PetgoraBackend.service.IPetAccessoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetAccessoryServiceImp implements IPetAccessoryService {

    @Autowired
    private PetAccessoryRepository repository;

    @Override
    public List<PetAccessoryDto> findAll() {
        return repository.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PetAccessoryDto> findById(Long id) {
        return repository.findById(id).map(this::convertEntityToDto);
    }

    @Override
    public PetAccessoryDto save(PetAccessoryDto petAccessoryDto) {
        PetAccessory petAccessory = convertDtoToEntity(petAccessoryDto);
        PetAccessory savedPetAccessory = repository.save(petAccessory);
        return convertEntityToDto(savedPetAccessory);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private PetAccessoryDto convertEntityToDto(PetAccessory petAccessory) {
        return new PetAccessoryDto(
                petAccessory.getId(),
                petAccessory.getName(),
                petAccessory.getPrice(),
                petAccessory.getImageUrl()
        );
    }

    private PetAccessory convertDtoToEntity(PetAccessoryDto petAccessoryDto) {
        PetAccessory petAccessory = new PetAccessory();
        petAccessory.setId(petAccessoryDto.id());
        petAccessory.setName(petAccessoryDto.name());
        petAccessory.setPrice(petAccessoryDto.price());
        petAccessory.setImageUrl(petAccessoryDto.imageUrl());
        return petAccessory;
    }
    @Override
    public List<PetAccessory> findByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }
}
