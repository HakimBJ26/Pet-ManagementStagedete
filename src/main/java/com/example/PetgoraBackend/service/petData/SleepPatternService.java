package com.example.PetgoraBackend.service.petData;


import com.example.PetgoraBackend.dto.petData.SleepPatternDTO;

import java.util.List;

public interface SleepPatternService {
    SleepPatternDTO createSleepPattern(SleepPatternDTO sleepPatternDTO);
    List<SleepPatternDTO> getSleepPatternsByPetId(Long petId);
    void deleteSleepPattern(Long sleepPatternId);  // Add this method
}
