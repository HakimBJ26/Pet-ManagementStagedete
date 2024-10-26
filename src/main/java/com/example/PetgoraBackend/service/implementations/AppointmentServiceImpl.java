package com.example.PetgoraBackend.service.implementations;

import com.example.PetgoraBackend.dto.AppointmentDto;
import com.example.PetgoraBackend.entity.Appointment;
import com.example.PetgoraBackend.entity.User;
import com.example.PetgoraBackend.mapper.AppointmentMapper;
import com.example.PetgoraBackend.repository.AppointmentRepository;
import com.example.PetgoraBackend.repository.UsersRepo;
import com.example.PetgoraBackend.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements IAppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UsersRepo userRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {
        User client = userRepository.findById(appointmentDto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        User veterinarian = userRepository.findById(appointmentDto.getVeterinarianId())
                .orElseThrow(() -> new RuntimeException("Veterinarian not found"));

        Appointment appointment = appointmentMapper.toEntity(appointmentDto);
        appointment.setClient(client);
        appointment.setVeterinarian(veterinarian);
        appointment.setStatus("PENDING");

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDto(savedAppointment);
    }

    @Override
    public AppointmentDto updateAppointmentStatus(Integer appointmentId, String status) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus(status);
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDto(updatedAppointment);
    }

    @Override
    public List<AppointmentDto> getAppointmentsByVeterinarianId(Integer veterinarianId) {
        return appointmentRepository.findByVeterinarianIdAndStatus(veterinarianId, "PENDING").stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDto> getAppointmentsByClientId(Integer clientId) {
        return appointmentRepository.findByClientId(clientId).stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDto approveAppointment(Integer appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus("APPROVED");
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDto(updatedAppointment);
    }
}
