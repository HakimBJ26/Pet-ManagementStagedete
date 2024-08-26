package com.example.PetgoraBackend.service.implementations;


import com.example.PetgoraBackend.dto.*;
import com.example.PetgoraBackend.entity.*;
import com.example.PetgoraBackend.repository.PetRepo;
import com.example.PetgoraBackend.repository.UsersRepo;
import com.example.PetgoraBackend.repository.petData.SafeZoneRepository;
import com.example.PetgoraBackend.service.IPetSafeZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
public PetDto addSafeZonesToPet(Integer petId, Map<SafeZoneType, List<PositionPetDto>> safeZoneDtos) {
    Pet pet = petRepo.findById(petId)
            .orElseThrow(() -> new IllegalArgumentException("Pet not found"));
    for (Map.Entry<SafeZoneType, List<PositionPetDto>> entry : safeZoneDtos.entrySet()) {
        SafeZoneType type = entry.getKey();
        List<PositionPetDto> positionDtos = entry.getValue();
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
//
public PetDto addSinglePositionToSafeZone(Integer petId, SafeZoneType type, PositionPetDto positionDto) {
         System.out.println("Attempting to add or update position in safe zone for petId: " + petId);
        System.out.println("SafeZoneType: " + type);
        if (positionDto == null) {
            System.out.println("Error: PositionDto is null for petId: " + petId);
            throw new IllegalArgumentException("PositionDto must not be null");  }
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> {
                    System.out.println("Error: Pet not found with id: " + petId);
                    return new IllegalArgumentException("Pet not found"); });
        System.out.println("Pet found: " + pet);
        Position newPosition = new Position(positionDto.lat(), positionDto.lng());
        System.out.println("Position to add or update: " + newPosition);

        Optional<SafeZone> existingSafeZone = pet.getSafeZones().stream()
                .filter(safeZone -> safeZone.getType().equals(type))
                .findFirst();
        if (existingSafeZone.isPresent()) {
            SafeZone safeZone = existingSafeZone.get();
            System.out.println("Existing safe zone found: " + safeZone);
            Optional<Position> existingPosition = safeZone.getPositions().stream()
                    .filter(pos -> pos.equals(newPosition))
                    .findFirst();
            if (existingPosition.isPresent()) {
                System.out.println("Existing position found. Updating coordinates.");
                Position posToUpdate = existingPosition.get();
                posToUpdate.setLat(positionDto.lat());
                posToUpdate.setLng(positionDto.lng());
            } else {
                System.out.println("Position does not exist. Adding new position.");
                safeZone.getPositions().add(newPosition); }
        } else {
            System.out.println("No existing safe zone found. Creating a new one.");
            SafeZone safeZone = new SafeZone();
            safeZone.setType(type);
            safeZone.setPositions(new ArrayList<>(Collections.singletonList(newPosition)));
            pet.getSafeZones().add(safeZone); }
        Pet updatedPet = petRepo.save(pet);
        System.out.println("Pet saved successfully with updated safe zones: " + updatedPet);

        return convertPetToDto(updatedPet);
    }

//    public PetDto addSinglePositionToSafeZone(Integer petId, SafeZoneType type, List<PositionPetDto> positionDtos) {
//
//            System.out.println("Attempting to add or update positions in safe zone for petId: " + petId);
//            System.out.println("SafeZoneType: " + type);
//
//            // Vérifier que la liste de positions est exactement de 3 positions
//            if (positionDtos == null || positionDtos.size() != 3) {
//                System.out.println("Error: Number of positions in the request must be exactly 3.");
//                throw new IllegalArgumentException("The number of positions must be exactly 3.");
//            }
//
//            Pet pet = petRepo.findById(petId)
//                    .orElseThrow(() -> {
//                        System.out.println("Error: Pet not found with id: " + petId);
//                        return new IllegalArgumentException("Pet not found");
//                    });
//            System.out.println("Pet found: " + pet);
//
//            Optional<SafeZone> existingSafeZone = pet.getSafeZones().stream()
//                    .filter(safeZone -> safeZone.getType().equals(type))
//                    .findFirst();
//
//            if (existingSafeZone.isPresent()) {
//                SafeZone safeZone = existingSafeZone.get();
//                System.out.println("Existing safe zone found: " + safeZone);
//
//                // Vérifier si la zone de sécurité a déjà 3 positions
////                if (safeZone.getPositions().size() > 0) {
////                    System.out.println("Error: The safe zone already contains positions. It must be reset to add new ones.");
////                    throw new IllegalArgumentException("The safe zone must be empty or reset before adding exactly 3 new positions.");
////                }
//
//                // Ajouter les positions
//                safeZone.setPositions(positionDtos.stream()
//                        .map(positionDto -> new Position(positionDto.lat(), positionDto.lng()))
//                        .collect(Collectors.toList()));
//
//            } else {
//                System.out.println("No existing safe zone found. Creating a new one.");
//
//                // Créer une nouvelle zone de sécurité avec les 3 positions
//                SafeZone safeZone = new SafeZone();
//                safeZone.setType(type);
//                safeZone.setPositions(positionDtos.stream()
//                        .map(positionDto -> new Position(positionDto.lat(), positionDto.lng()))
//                        .collect(Collectors.toList()));
//
//                pet.getSafeZones().add(safeZone);
//            }
//
//            Pet updatedPet = petRepo.save(pet);
//            System.out.println("Pet saved successfully with updated safe zones: " + updatedPet);
//
//            return convertPetToDto(updatedPet);
//        }



        public List<Position> getPositionsByPetIdAndType(Integer petId, SafeZoneType type) {
    List<SafeZone> safeZones = safeZoneRepository.findSafeZonesByPetIdAndType(petId, type);
    System.out.println("Safe Zones: " + safeZones);
    return safeZones.stream()
            .flatMap(sz -> sz.getPositions().stream())
            .collect(Collectors.toList());
}

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
