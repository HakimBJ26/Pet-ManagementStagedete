package com.example.PetgoraBackend.controller;

import com.example.PetgoraBackend.service.DeviceService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    private final DeviceService deviceService;
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }
    @PostMapping("/authenticate")
    public Map<String, String> authenticateDevice(@RequestParam Long deviceId, @RequestParam String secretPass) {
        String token = deviceService.generateTokenForDevice(deviceId, secretPass);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
}
