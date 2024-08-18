package com.example.PetgoraBackend.mapper.petData;

import com.example.PetgoraBackend.dto.petData.PetGoalDto;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.entity.petData.PetGoal;
import com.example.PetgoraBackend.repository.PetRepo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
@Mapper(componentModel = "spring")
public abstract class PetGoalMapper {

    @Autowired
    private PetRepo petRepository;

    @Mapping(target = "pet", source = "petId", qualifiedByName = "mapPetIdToPet")
    public abstract PetGoal toEntity(PetGoalDto petGoalDTO);

    @Mapping(target = "petId", source = "pet.id")
    public abstract PetGoalDto toDTO(PetGoal petGoal);

    @Named("mapPetIdToPet")
    public Pet mapPetIdToPet(Integer petId) {
        if (petId != null) {
            return petRepository.findById(petId).orElse(null);
        }
        return null;
    }
}
