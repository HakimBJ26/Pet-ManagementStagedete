package com.example.PetgoraBackend.service.implementations.petData;

import com.example.PetgoraBackend.dto.petData.SleepPatternDTO;
import com.example.PetgoraBackend.entity.petData.SleepPattern;
import com.example.PetgoraBackend.mapper.petData.SleepPatternMapper;
import com.example.PetgoraBackend.repository.petData.SleepPatternRepository;
import com.example.PetgoraBackend.service.petData.SleepPatternService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SleepPatternServiceImpl implements SleepPatternService {

    private final SleepPatternRepository sleepPatternRepository;
    private final SleepPatternMapper sleepPatternMapper;

    public SleepPatternServiceImpl(SleepPatternRepository sleepPatternRepository, SleepPatternMapper sleepPatternMapper) {
        this.sleepPatternRepository = sleepPatternRepository;
        this.sleepPatternMapper = sleepPatternMapper;
    }

    @Override
    public SleepPatternDTO createSleepPattern(SleepPatternDTO sleepPatternDTO) {
        SleepPattern sleepPattern = sleepPatternMapper.toEntity(sleepPatternDTO);
        SleepPattern savedSleepPattern = sleepPatternRepository.save(sleepPattern);
        return sleepPatternMapper.toDto(savedSleepPattern);
    }

    @Override
    public List<SleepPatternDTO> getSleepPatternsByPetId(Long petId) {
        List<SleepPattern> sleepPatterns = sleepPatternRepository.findTop6ByPetIdOrderByDayDesc(petId);
        return sleepPatterns.stream()
                .map(sleepPatternMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSleepPattern(Long sleepPatternId) {
        sleepPatternRepository.deleteById(sleepPatternId); // Implement delete functionality
    }
}
