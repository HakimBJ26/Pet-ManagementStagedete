package com.example.PetgoraBackend.repository.petData;



import com.example.PetgoraBackend.entity.petData.SleepPattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SleepPatternRepository extends JpaRepository<SleepPattern, Long> {
    List<SleepPattern> findTop6ByPetIdOrderByDayDesc(Long petId);
}
