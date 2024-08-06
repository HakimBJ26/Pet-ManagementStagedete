package com.example.PetgoraBackend.service.implementations;

import com.example.PetgoraBackend.dto.HealthPassportDto;
import com.example.PetgoraBackend.entity.HealthPassport;
import com.example.PetgoraBackend.mapper.HealthPassportMapper;
import com.example.PetgoraBackend.repository.HealthPassportRepo;
import com.example.PetgoraBackend.service.IHealthPassportService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HealthPassportServiceImp implements IHealthPassportService {

    private final HealthPassportRepo healthPassportRepo;

    public HealthPassportServiceImp(HealthPassportRepo healthPassportRepo) {
        this.healthPassportRepo = healthPassportRepo;
    }

    @Override
    public HealthPassportDto getHealthPassportByPetId(Integer petId) {
        HealthPassport healthPassport = healthPassportRepo.findByPet_Id(petId);
        if (healthPassport == null) {
            throw new EntityNotFoundException("Health Passport not found for Pet ID " + petId);
        }
        return HealthPassportMapper.INSTANCE.toHealthPassportDto(healthPassport);
    }
}
