package com.example.PetgoraBackend.controller;

import com.example.PetgoraBackend.dto.PetAccessoryDto;
import com.example.PetgoraBackend.service.IPetAccessoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accessories")
public class PetAccessoryController {

    @Autowired
    private IPetAccessoryService service;
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<PetAccessoryDto> getAllAccessories() {
        return service.findAll();
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public Optional<PetAccessoryDto> getAccessoryById(@PathVariable Long id) {
        return service.findById(id);
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public PetAccessoryDto createAccessory(@RequestBody PetAccessoryDto accessoryDto) {
        return service.save(accessoryDto);
    }
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public PetAccessoryDto updateAccessory(@PathVariable Long id, @RequestBody PetAccessoryDto updatedAccessoryDto) {
        return service.findById(id)
                .map(existingAccessory -> {
                    PetAccessoryDto updatedDto = new PetAccessoryDto(
                            existingAccessory.id(),
                            updatedAccessoryDto.name(),
                            updatedAccessoryDto.price(),
                            updatedAccessoryDto.imageUrl()
                    );
                    return service.save(updatedDto);
                })
                .orElseGet(() -> {
                    PetAccessoryDto newAccessoryDto = new PetAccessoryDto(
                            id,
                            updatedAccessoryDto.name(),
                            updatedAccessoryDto.price(),
                            updatedAccessoryDto.imageUrl()
                    );
                    return service.save(newAccessoryDto);
                });
    }
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public void deleteAccessory(@PathVariable Long id) {
        service.deleteById(id);
    }
}
