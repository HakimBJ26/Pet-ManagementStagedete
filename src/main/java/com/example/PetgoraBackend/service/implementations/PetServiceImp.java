package com.example.PetgoraBackend.service.implementations;

import com.example.PetgoraBackend.dto.PetResponseDto;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.dto.PetDto;
import com.example.PetgoraBackend.entity.User; // Import User entity
import com.example.PetgoraBackend.mapper.PetMapper;
import com.example.PetgoraBackend.repository.PetRepo;
import com.example.PetgoraBackend.repository.UsersRepo; // Import UsersRepo for finding the owner
import com.example.PetgoraBackend.service.IPetService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class PetServiceImp implements IPetService {

    private final PetRepo petRepo;
    private final UsersRepo usersRepo; // Add UsersRepo to find the owner

    public PetServiceImp(PetRepo petRepo, UsersRepo usersRepo) {
        this.petRepo = petRepo;
        this.usersRepo = usersRepo;
    }

    @Override
    public ResponseEntity<String> deletePet(Integer petId) {
        petRepo.deleteById(petId);
        System.out.println("Pet deleted successfully");
        return ResponseEntity.ok("Pet deleted successfully");
    }

    @Override
    public PetDto addPet(PetDto petDto) {
        Pet pet = PetMapper.INSTANCE.toPet(petDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User owner = usersRepo.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        pet.setOwner(owner); // Set the owner

        return PetMapper.INSTANCE.toPetDto(petRepo.save(pet));
    }



    @Override
    public PetDto updatePet(Integer petId, PetDto petDto) {
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found"));

        pet.setName(petDto.name()); // Fix here to get name
        pet.setBreed(petDto.breed());
        pet.setAge(petDto.age());

        return PetMapper.INSTANCE.toPetDto(petRepo.save(pet));
    }


    @Override
    public List<PetResponseDto> getAllPets() {
        List<Pet> pets = petRepo.findAll();
        return pets.stream()
                .map(PetMapper.INSTANCE::toPetResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PetResponseDto getPetById(Integer petId) {
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found"));
        return PetMapper.INSTANCE.toPetResponseDto(pet);
    }

    @Override
    public List<PetDto> getPetsByOwnerEmail(String ownerEmail) {
        return null;
    }


    @Override
    public List<PetDto> getAllPetsByOwner(Integer Id) {
        List<Pet> pets = petRepo.findByOwner_Id(Id);
        return pets.stream()
                .map(PetMapper.INSTANCE::toPetDto)
                .collect(Collectors.toList());
    }
}
