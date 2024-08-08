package com.example.PetgoraBackend.service.petData;


import com.example.PetgoraBackend.dto.petData.VitalSignsDto;
import com.example.PetgoraBackend.entity.petData.VitalSigns;

public interface VitalSignsService {

    VitalSignsDto getVitalSignsByPetId(Integer petId);
    VitalSignsDto updateVitalSigns(Integer id, VitalSignsDto vitalSignsDTO);

    VitalSignsDto saveVitalSigns(VitalSignsDto vitalSignsDTO);
    void deleteVitalSigns(Integer id);
    public void saveOrUpdateVitalSigns(VitalSigns vitalSigns);

}

