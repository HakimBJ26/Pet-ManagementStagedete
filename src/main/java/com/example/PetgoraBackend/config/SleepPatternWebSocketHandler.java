package com.example.PetgoraBackend.config;

import com.example.PetgoraBackend.entity.petData.SleepPattern;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.repository.PetRepo;
import com.example.PetgoraBackend.repository.petData.SleepPatternRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class SleepPatternWebSocketHandler {

    private final SleepPatternRepository sleepPatternRepository;
    private final PetRepo petRepository;
    private final ObjectMapper mapper;

    public SleepPatternWebSocketHandler(SleepPatternRepository sleepPatternRepository, PetRepo petRepository, ObjectMapper mapper) {
        this.sleepPatternRepository = sleepPatternRepository;
        this.petRepository = petRepository;
        this.mapper = mapper;
    }

    public void sendSleepPatternData(String message) {
        try {
            JsonNode jsonNode = mapper.readTree(message);
            JsonNode sleepPattern = jsonNode.get("sleepData");
          if(sleepPattern !=null){
              Integer petId = sleepPattern.get("petId").asInt();
              Pet pet = petRepository.findById(petId).orElse(null);

              String duration = sleepPattern.get("duration").asText();
              String dayString = sleepPattern.get("day").asText();

              DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
              LocalDate day = LocalDate.parse(dayString, formatter);


              if (pet != null) {
                  SleepPattern newSleepPattern = new SleepPattern();
                  newSleepPattern.setPet(pet);
                  newSleepPattern.setDuration(duration);
                  newSleepPattern.setDay(day);
                  sleepPatternRepository.save(newSleepPattern);
                  System.out.println("Sleep pattern stored for pet " + petId);
              } else {
                  System.out.println("Pet not found for ID " + petId);
              }
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
