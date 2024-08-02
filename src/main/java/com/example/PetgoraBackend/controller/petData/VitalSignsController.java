package com.example.PetgoraBackend.controller.petData;



import com.example.PetgoraBackend.dto.petData.VitalSignsDto;
import com.example.PetgoraBackend.service.petData.VitalSignsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vital-signs")
public class VitalSignsController {

    @Autowired
    private VitalSignsService vitalSignsService;

    @GetMapping("/pet/{petId}")
    public VitalSignsDto getVitalSignsByPetId(@PathVariable Integer petId) {
        return vitalSignsService.getVitalSignsByPetId(petId);
    }

    @PostMapping
    public VitalSignsDto saveVitalSigns(@RequestBody VitalSignsDto vitalSignsDTO) {
        return vitalSignsService.saveVitalSigns(vitalSignsDTO);
    }

    @PutMapping("/{id}")
    public VitalSignsDto updateVitalSigns(@PathVariable Integer id, @RequestBody VitalSignsDto vitalSignsDTO) {
        return vitalSignsService.updateVitalSigns(id, vitalSignsDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVitalSigns(@PathVariable Integer id) {
        vitalSignsService.deleteVitalSigns(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
