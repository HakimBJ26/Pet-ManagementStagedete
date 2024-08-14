package com.example.PetgoraBackend.repository;

import com.example.PetgoraBackend.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findById(Long id);
    List<Device> findByIsActive(boolean isActive); // Define the method here

}

