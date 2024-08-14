package com.example.PetgoraBackend.service.implementations.petData;

import com.example.PetgoraBackend.dto.petData.VetAppointmentComparisonDTO;
import com.example.PetgoraBackend.dto.petData.VetAppointmentDTO;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.entity.petData.VetAppointment;
import com.example.PetgoraBackend.mapper.petData.VetAppointmentMapper;
import com.example.PetgoraBackend.repository.PetRepo;
import com.example.PetgoraBackend.repository.petData.VetAppointmentRepository;
import com.example.PetgoraBackend.service.petData.VetAppointmentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class VetAppointmentServiceImpl implements VetAppointmentService {

    private final VetAppointmentRepository vetAppointmentRepository;
    private final PetRepo petRepository;
    private final VetAppointmentMapper vetAppointmentMapper;

    public VetAppointmentServiceImpl(VetAppointmentRepository vetAppointmentRepository, PetRepo petRepository, VetAppointmentMapper vetAppointmentMapper) {
        this.vetAppointmentRepository = vetAppointmentRepository;
        this.petRepository = petRepository;
        this.vetAppointmentMapper = vetAppointmentMapper;
    }

    @Override
    public VetAppointmentDTO createVetAppointment(VetAppointmentDTO vetAppointmentDTO, Integer petId) {
        ensureVeterinarianRole();

        Pet pet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet not found"));
        VetAppointment vetAppointment = vetAppointmentMapper.toEntity(vetAppointmentDTO);
        vetAppointment.setPet(pet);
        VetAppointment savedAppointment = vetAppointmentRepository.save(vetAppointment);
        return vetAppointmentMapper.toDto(savedAppointment);
    }

    @Override
    public VetAppointmentComparisonDTO getLastTwoVetAppointments(Integer petId) {
        List<VetAppointment> appointments = vetAppointmentRepository.findByPetIdOrderByAppointmentDateDesc(petId);

        if (appointments.isEmpty()) {
            throw new RuntimeException("No vet appointments found for this pet");
        }

        VetAppointmentDTO lastAppointmentDTO = vetAppointmentMapper.toDto(appointments.get(0));
        VetAppointmentComparisonDTO comparisonDTO = new VetAppointmentComparisonDTO();
        comparisonDTO.setLastAppointment(lastAppointmentDTO);

        if (appointments.size() > 1) {
            VetAppointment lastAppointment = appointments.get(0);
            VetAppointment beforeLastAppointment = appointments.get(1);

            // Comparison logic
            comparisonDTO.setWeightComparison(calculatePercentageChange(lastAppointment.getWeight(), beforeLastAppointment.getWeight()));
            comparisonDTO.setBodyComparison(calculatePercentageChange(lastAppointment.getBody(), beforeLastAppointment.getBody()));
            comparisonDTO.setTailComparison(calculatePercentageChange(lastAppointment.getTail(), beforeLastAppointment.getTail()));
            comparisonDTO.setChestComparison(calculatePercentageChange(lastAppointment.getChest(), beforeLastAppointment.getChest()));

        } else {
            // If there's only one appointment, add "-" for all comparison fields
            comparisonDTO.setWeightComparison("-");
            comparisonDTO.setBodyComparison("-");
            comparisonDTO.setTailComparison("-");
            comparisonDTO.setChestComparison("-");
        }

        return comparisonDTO;
    }

    private String calculatePercentageChange(double current, double previous) {
        if (previous == 0) {
            return "-"; // Avoid division by zero
        }
        double change = ((current - previous) / previous) * 100;
        return String.format("%.2f%%", change);
    }

    private void ensureVeterinarianRole() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        if (!userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_VETERINARIAN"))) {
            throw new RuntimeException("Only veterinarians can create vet appointments");
        }
    }


    @Override
    public void deleteVetAppointment(Integer id) {
        if (!vetAppointmentRepository.existsById(id)) {
            throw new NoSuchElementException("Vet appointment not found");
        }
        vetAppointmentRepository.deleteById(id);
    }

    @Override
    public VetAppointmentDTO updateVetAppointment(Integer id, VetAppointmentDTO vetAppointmentDTO) {
        VetAppointment existingAppointment = vetAppointmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Vet appointment not found"));

        // Map updated fields from DTO to entity
        existingAppointment.setWeight(vetAppointmentDTO.getWeight());
        existingAppointment.setBody(vetAppointmentDTO.getBody());
        existingAppointment.setTail(vetAppointmentDTO.getTail());
        existingAppointment.setChest(vetAppointmentDTO.getChest());
        existingAppointment.setAppointmentDate(vetAppointmentDTO.getAppointmentDate());

        // Save the updated appointment
        VetAppointment updatedAppointment = vetAppointmentRepository.save(existingAppointment);

        // Return the updated DTO
        return vetAppointmentMapper.toDto(updatedAppointment);
    }
}
