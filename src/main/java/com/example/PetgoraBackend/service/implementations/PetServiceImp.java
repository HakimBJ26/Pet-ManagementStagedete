package com.example.PetgoraBackend.service.implementations;

import com.example.PetgoraBackend.dto.PetResponseDto;
import com.example.PetgoraBackend.dto.PetsDTO;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.dto.PetDto;
import com.example.PetgoraBackend.entity.User; // Import User entity
import com.example.PetgoraBackend.mapper.PetMapper;
import com.example.PetgoraBackend.repository.PetRepo;
import com.example.PetgoraBackend.repository.UsersRepo; // Import UsersRepo for finding the owner
import com.example.PetgoraBackend.repository.alerts.HealthAlertRepository;
import com.example.PetgoraBackend.repository.petData.OverviewRepository;
import com.example.PetgoraBackend.repository.petData.SafeZoneRepository;
import com.example.PetgoraBackend.repository.petData.VitalSignsRepository;
import com.example.PetgoraBackend.service.IPetService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetServiceImp implements IPetService {

    private final PetRepo petRepo;
    private VitalSignsRepository vitalSignsRepository ;
    private HealthAlertRepository healthAlertRepository ;
    private OverviewRepository overviewRepository ;
    private final UsersRepo usersRepo;


    public PetServiceImp(PetRepo petRepo, UsersRepo usersRepo,VitalSignsRepository vitalSignsRepository,
                         HealthAlertRepository healthAlertRepository,OverviewRepository overviewRepository,
                         SafeZoneRepository safeZoneRepository) {
        this.petRepo = petRepo;
        this.usersRepo = usersRepo;
        this.healthAlertRepository=healthAlertRepository ;
        this.vitalSignsRepository=vitalSignsRepository ;
        this.overviewRepository=overviewRepository;


    }

    @Override
    public ResponseEntity<String> deletePet(Integer petId) {
        Pet pet = petRepo.findById(petId).orElseThrow(() -> new EntityNotFoundException("Pet not found"));

        vitalSignsRepository.deleteByPetId(petId);
        healthAlertRepository.deleteByPetId(petId);
        overviewRepository.deleteByPetId(petId);

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
        // check if it's the owner
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User owner = usersRepo.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found"));
        if (pet.getOwner().getId() != owner.getId()) {
            throw new EntityNotFoundException("You are not the owner of this pet");
        }

        pet.setName(petDto.name());
        pet.setBreed(petDto.breed());
        pet.setAge(petDto.age());

        return PetMapper.INSTANCE.toPetDto(petRepo.save(pet));
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<PetResponseDto> getAllPets() {
        List<Pet> pets = petRepo.findAll();
        return pets.stream()
                .map(PetMapper.INSTANCE::toPetResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PetResponseDto getPetById(Integer petId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User owner = usersRepo.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found"));
        if (pet.getOwner().getId() != owner.getId()) {
            throw new EntityNotFoundException("You are not the owner of this pet");
        }

        return PetMapper.INSTANCE.toPetResponseDto(pet);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<PetDto> getAllPetsByOwner(Integer Id) {
        List<Pet> pets = petRepo.findByOwner_Id(Id);
        return pets.stream()
                .map(PetMapper.INSTANCE::toPetDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PetsDTO> getCurrentUserPets() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User owner = usersRepo.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

        List<Pet> pets = petRepo.findByOwner_Id(owner.getId());

        // Convert the list of Pet entities to a list of PetDTOs
        List<PetsDTO> petDTOs = pets.stream()
                .map(pet -> new PetsDTO(pet.getId(), pet.getName(), pet.getBreed(), pet.getAge(), pet.getImageUrl()))
                .collect(Collectors.toList());

        return petDTOs;
    }



    @Override
    public Pet uploadPetImage(Integer petId, byte[] image) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User owner = usersRepo.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found"));
        if (pet.getOwner().getId() != owner.getId()) {
            throw new EntityNotFoundException("You are not the owner of this pet");
        }

        pet.setImage(image);
        return petRepo.save(pet);
    }


    @Override
    public PetsDTO updatePetImageUrl(Integer petId, String imageUrl) {
        // Get the authenticated user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Find the pet owner
        User owner = usersRepo.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

        // Find the pet by ID
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found"));

        // Ensure the authenticated user is the owner of the pet
        if (!pet.getOwner().getId().equals(owner.getId())) {
            throw new EntityNotFoundException("You are not the owner of this pet");
        }

        // Update the pet's image URL
        pet.setImageUrl(imageUrl);

        // Save the updated pet
        petRepo.save(pet);

        // Return the updated PetDTO
        return new PetsDTO(pet.getId(), pet.getName(), pet.getBreed(), pet.getAge(), pet.getImageUrl());
    }


}