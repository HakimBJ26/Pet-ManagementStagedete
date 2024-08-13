package com.example.PetgoraBackend.repository.petData;

import com.example.PetgoraBackend.entity.petData.VetAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VetAppointmentRepository extends JpaRepository<VetAppointment, Integer> {
    List<VetAppointment> findByPetIdOrderByAppointmentDateDesc(Integer petId);
}
