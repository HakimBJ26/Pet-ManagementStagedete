package com.example.PetgoraBackend.repository;

import com.example.PetgoraBackend.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByVeterinarianIdAndStatus(Integer veterinarianId, String status);
    List<Appointment> findByClientId(Integer clientId);
}
