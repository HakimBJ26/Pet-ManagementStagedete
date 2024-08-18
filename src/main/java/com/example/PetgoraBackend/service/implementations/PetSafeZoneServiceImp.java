package com.example.PetgoraBackend.service.implementations;


import com.example.PetgoraBackend.dto.DangerZoneDto;
import com.example.PetgoraBackend.dto.PetDto;
import com.example.PetgoraBackend.dto.PositionPetDto;
import com.example.PetgoraBackend.dto.SafeZoneDto;
import com.example.PetgoraBackend.entity.*;
import com.example.PetgoraBackend.repository.PetRepo;
import com.example.PetgoraBackend.repository.UsersRepo;
import com.example.PetgoraBackend.repository.petData.SafeZoneRepository;
import com.example.PetgoraBackend.service.IPetSafeZoneService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@Service

public class PetSafeZoneServiceImp implements IPetSafeZoneService {

    private final PetRepo petRepo;
    private final SafeZoneRepository safeZoneRepository;
    private  final  UsersRepo usersRepo;

    @Autowired
    public PetSafeZoneServiceImp(PetRepo petRepo, SafeZoneRepository safeZoneRepository, UsersRepo usersRepo) {
        this.petRepo = petRepo;
        this.safeZoneRepository = safeZoneRepository;
        this.usersRepo = usersRepo;
    }

//    @Override
//    public PetDto addSafeZoneToPet(Integer petId, List<PositionPetDto> positionDtos) {
//        Pet pet = petRepo.findById(petId)
//                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));
//
//        if (positionDtos.size() < 3) {
//            throw new IllegalArgumentException("A safe zone must have at least 3 points.");
//        }
//
//        List<Position> positions = positionDtos.stream()
//                .map(dto -> new Position(dto.lat(), dto.lng()))
//                .collect(Collectors.toList());
//
//        SafeZone safeZone = new SafeZone();
//        safeZone.setPositions(positions);
//
//        pet.getSafeZones().add(safeZone);
//        Pet updatedPet = petRepo.save(pet);
//
//        return convertPetToDto(updatedPet);
//    }
public PetDto addSafeZonesToPet(Integer petId, Map<SafeZoneType, List<PositionPetDto>> safeZoneDtos) {
    Pet pet = petRepo.findById(petId)
            .orElseThrow(() -> new IllegalArgumentException("Pet not found"));

    for (Map.Entry<SafeZoneType, List<PositionPetDto>> entry : safeZoneDtos.entrySet()) {
        SafeZoneType type = entry.getKey();
        List<PositionPetDto> positionDtos = entry.getValue();

//        if (positionDtos.size() < 3) {
//            throw new IllegalArgumentException("A safe zone must have at least 3 points.");
//        }

        List<Position> positions = positionDtos.stream()
                .map(dto -> new Position(dto.lat(), dto.lng()))
                .collect(Collectors.toList());

        Optional<SafeZone> existingSafeZone = pet.getSafeZones().stream()
                .filter(safeZone -> safeZone.getType().equals(type))
                .findFirst();

        if (existingSafeZone.isPresent()) {
            existingSafeZone.get().setPositions(positions);
        } else {
            SafeZone safeZone = new SafeZone();
            safeZone.setType(type);
            safeZone.setPositions(positions);
            pet.getSafeZones().add(safeZone);
        }
    }

    Pet updatedPet = petRepo.save(pet);
    return convertPetToDto(updatedPet);
}

public List<Position> getPositionsByPetIdAndType(Integer petId, SafeZoneType type) {
    List<SafeZone> safeZones = safeZoneRepository.findSafeZonesByPetIdAndType(petId, type);
    System.out.println("Safe Zones: " + safeZones); // Log pour débogage
    return safeZones.stream()
            .flatMap(sz -> sz.getPositions().stream())
            .collect(Collectors.toList());
}




//
//    @Override
//    public PetDto updateSafeZone(Integer petId, Long safeZoneId, List<PositionPetDto> positionDtos) {
//        Pet pet = petRepo.findById(petId)
//                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));
//
//        SafeZone safeZone = pet.getSafeZones().stream()
//                .filter(sz -> sz.getId().equals(safeZoneId))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("SafeZone not found"));
//
//        List<Position> positions = positionDtos.stream()
//                .map(dto -> new Position(dto.lat(), dto.lng()))
//                .collect(Collectors.toList());
//
//        safeZone.setPositions(positions);
//        Pet updatedPet = petRepo.save(pet);
//
//        return convertPetToDto(updatedPet);
//    }
@Override
public PetDto updateSafeZoneByType(Integer petId, SafeZoneType type, List<PositionPetDto> positionDtos) {
    Pet pet = petRepo.findById(petId)
            .orElseThrow(() -> new IllegalArgumentException("Pet not found"));

    SafeZone safeZone = pet.getSafeZones().stream()
            .filter(sz -> sz.getType().equals(type))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("SafeZone not found"));

    List<Position> positions = positionDtos.stream()
            .map(dto -> new Position(dto.lat(), dto.lng()))
            .collect(Collectors.toList());

    safeZone.setPositions(positions);
    Pet updatedPet = petRepo.save(pet);

    return convertPetToDto(updatedPet);
}



    @Override
    public PetDto deleteSafeZone(Integer petId, Long safeZoneId) {
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));

        pet.getSafeZones().removeIf(sz -> sz.getId().equals(safeZoneId));
        Pet updatedPet = petRepo.save(pet);

        return convertPetToDto(updatedPet);
    }

    @Override
    public List<SafeZoneDto> getSafeZonesByPet(Integer petId) {
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));

        return pet.getSafeZones().stream()
                .map(this::convertSafeZoneToDto)
                .collect(Collectors.toList());
    }


    private SafeZoneDto convertSafeZoneToDto(SafeZone safeZone) {
        List<PositionPetDto> positionDtos = safeZone.getPositions().stream()
                .map(pos -> new PositionPetDto(pos.getLat(), pos.getLng()))
                .collect(Collectors.toList());

        return new SafeZoneDto(safeZone.getId(), positionDtos);
    }

    private PetDto convertPetToDto(Pet pet) {
        return new PetDto(
                pet.getName(),
                pet.getBreed(),
                pet.getAge(),
                pet.getSafeZones().stream()
                        .map(this::convertSafeZoneToDto)
                        .collect(Collectors.toList()),
                pet.getDangerZones().stream()
                        .map(this::convertDangerZoneToDto)
                        .collect(Collectors.toList())  );
    }

    private DangerZoneDto convertDangerZoneToDto(DangerZone dangerZone) {
        List<PositionPetDto> positionDtos = dangerZone.getPositions().stream()
                .map(pos -> new PositionPetDto(pos.getLat(), pos.getLng()))
                .collect(Collectors.toList());

        return new DangerZoneDto(dangerZone.getId(), positionDtos);
    }
}
