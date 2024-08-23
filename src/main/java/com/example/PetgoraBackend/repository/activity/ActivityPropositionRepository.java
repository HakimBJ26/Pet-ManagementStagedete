package com.example.PetgoraBackend.repository.activity;


import com.example.PetgoraBackend.entity.acitvity.ActivityProposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ActivityPropositionRepository extends JpaRepository<ActivityProposition, Long> {
    List<ActivityProposition> findTop2ByOrderByDayDesc();
}
