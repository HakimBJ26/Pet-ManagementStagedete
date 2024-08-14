package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.config.DeviceJWTUtils;
import com.example.PetgoraBackend.entity.Device;
import com.example.PetgoraBackend.repository.DeviceRepository;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    private final DeviceJWTUtils deviceJWTUtils;
    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceJWTUtils deviceJWTUtils, DeviceRepository deviceRepository) {
        this.deviceJWTUtils = deviceJWTUtils;
        this.deviceRepository = deviceRepository;
    }

    public String generateTokenForDevice(Long deviceId, String secretPass) {
        // Retrieve the device from the repository
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));

        // Validate credentials
        if (!device.getSecretPass().equals(secretPass)) {
            throw new RuntimeException("Invalid credentials");
        }

        // Activate the device and save the token
        device.setActive(true); // Set the device as active
        String token = deviceJWTUtils.generateToken(deviceId.toString());
        device.setToken(token); // Store the generated token in the device entity
        deviceRepository.save(device); // Save the updated device

        // Return the token
        return token;
    }
}
