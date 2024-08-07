package com.example.PetgoraBackend.service.implementations;

import com.example.PetgoraBackend.dto.DangerZoneDto;
import com.example.PetgoraBackend.dto.PetDto;
import com.example.PetgoraBackend.dto.PositionPetDto;
import com.example.PetgoraBackend.dto.SafeZoneDto;
import com.example.PetgoraBackend.entity.DangerZone;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.entity.Position;
import com.example.PetgoraBackend.entity.SafeZone;
import com.example.PetgoraBackend.repository.PetRepo;
import com.example.PetgoraBackend.repository.UsersRepo;
import com.example.PetgoraBackend.service.IPetDangerZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class DangerZoneServiceImp implements IPetDangerZoneService {

    private final PetRepo petRepo;


    @Autowired
    public DangerZoneServiceImp(PetRepo petRepo) {
        this.petRepo = petRepo;

    }

    @Override
    public List<DangerZoneDto> getDangerZonesByPet(Integer petId) {
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));

        return pet.getDangerZones().stream()
                .map(this::convertDangerZoneToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PetDto addDangerZoneToPet(Integer petId, List<PositionPetDto> positionDtos) {
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));

        if (positionDtos.size() < 4) {
            throw new IllegalArgumentException("A danger zone must have at least 4 points.");
        }

        List<Position> positions = positionDtos.stream()
                .map(dto -> new Position(dto.lat(), dto.lng()))
                .collect(Collectors.toList());

        DangerZone dangerZone = new DangerZone();
        dangerZone.setPositions(positions);

        pet.getDangerZones().add(dangerZone);
        Pet updatedPet = petRepo.save(pet);

        return convertPetToDto(updatedPet); // Use convertPetToDto here
    }

    @Override
    public PetDto updateDangerZone(Integer petId, Long dangerZoneId, List<PositionPetDto> positionDtos) {
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));

        DangerZone dangerZone = pet.getDangerZones().stream()
                .filter(dz -> dz.getId().equals(dangerZoneId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("DangerZone not found"));

        List<Position> positions = positionDtos.stream()
                .map(dto -> new Position(dto.lat(), dto.lng()))
                .collect(Collectors.toList());

        dangerZone.setPositions(positions);
        Pet updatedPet = petRepo.save(pet);

        return convertPetToDto(updatedPet); // Use convertPetToDto here
    }

    @Override
    public PetDto deleteDangerZone(Integer petId, Long dangerZoneId) {
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));

        pet.getDangerZones().removeIf(dz -> dz.getId().equals(dangerZoneId));
        Pet updatedPet = petRepo.save(pet);

        return convertPetToDto(updatedPet); // Use convertPetToDto here
    }

    private DangerZoneDto convertDangerZoneToDto(DangerZone dangerZone) {
        List<PositionPetDto> positionDtos = dangerZone.getPositions().stream()
                .map(pos -> new PositionPetDto(pos.getLat(), pos.getLng()))
                .collect(Collectors.toList());

        return new DangerZoneDto(dangerZone.getId(), positionDtos);
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
                        .collect(Collectors.toList())
        );
    }
}
