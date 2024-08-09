package com.example.PetgoraBackend.service.implementations.petData;


import com.example.PetgoraBackend.dto.petData.VitalSignsDto;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.entity.petData.VitalSigns;
import com.example.PetgoraBackend.exception.RessourceNotFoundException;
import com.example.PetgoraBackend.mapper.petData.VitalSignsMapper;
import com.example.PetgoraBackend.repository.PetRepo;
import com.example.PetgoraBackend.repository.petData.VitalSignsRepository;
import com.example.PetgoraBackend.service.petData.VitalSignsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VitalSignsServiceImpl implements VitalSignsService {

    @Autowired
    private VitalSignsRepository vitalSignsRepository;

    @Autowired
    private PetRepo petRepository;

    @Autowired
    private VitalSignsMapper vitalSignsMapper;

    @Override
    public VitalSignsDto getVitalSignsByPetId(Integer petId) {
        Optional<VitalSigns> optionalVitalSigns = vitalSignsRepository.findById(petId);
        return optionalVitalSigns.map(vitalSignsMapper::toDTO).orElse(null);
    }

    @Override
    public VitalSignsDto saveVitalSigns(VitalSignsDto vitalSignsDTO) {
        Pet pet = petRepository.findById(vitalSignsDTO.getPetId()).orElseThrow(() -> new RessourceNotFoundException("Pet not found"));
        VitalSigns vitalSigns = vitalSignsMapper.toEntity(vitalSignsDTO, pet);
        VitalSigns savedVitalSigns = vitalSignsRepository.save(vitalSigns);
        return vitalSignsMapper.toDTO(savedVitalSigns);
    }

    @Override
    public VitalSignsDto updateVitalSigns(Integer id, VitalSignsDto vitalSignsDTO) {
        VitalSigns existingVitalSigns = vitalSignsRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException("VitalSigns not found"));
        Pet pet = petRepository.findById(vitalSignsDTO.getPetId()).orElseThrow(() -> new RessourceNotFoundException("Pet not found"));

        existingVitalSigns.setHeartRate(vitalSignsDTO.getHeartRate());
        existingVitalSigns.setTemperature(vitalSignsDTO.getTemperature());
        existingVitalSigns.setActivityLevel(vitalSignsDTO.getActivityLevel());
        existingVitalSigns.setLastUpdated(vitalSignsDTO.getLastUpdated());
        existingVitalSigns.setPet(pet);

        VitalSigns updatedVitalSigns = vitalSignsRepository.save(existingVitalSigns);
        return vitalSignsMapper.toDTO(updatedVitalSigns);
    }

    @Override
    public void deleteVitalSigns(Integer id) {
        if (!vitalSignsRepository.existsById(id)) {
            throw new RessourceNotFoundException("VitalSigns not found");
        }
        vitalSignsRepository.deleteById(id);
    }
    @Override
    public void saveOrUpdateVitalSigns(VitalSigns vitalSigns) {
        Pet pet = petRepository.findById(vitalSigns.getPet().getId())
                .orElseThrow(() -> new EntityNotFoundException("Pet not found"));

        // Attach the managed Pet entity to VitalSigns
        vitalSigns.setPet(pet);

        VitalSigns existingVitalSigns = vitalSignsRepository.findByPetId(pet.getId());
        if (existingVitalSigns != null) {
            existingVitalSigns.setHeartRate(vitalSigns.getHeartRate());
            existingVitalSigns.setTemperature(vitalSigns.getTemperature());
            existingVitalSigns.setActivityLevel(vitalSigns.getActivityLevel());
            existingVitalSigns.setLastUpdated(vitalSigns.getLastUpdated());
            vitalSignsRepository.save(existingVitalSigns);
        } else {
            vitalSignsRepository.save(vitalSigns);
        }
    }


}

