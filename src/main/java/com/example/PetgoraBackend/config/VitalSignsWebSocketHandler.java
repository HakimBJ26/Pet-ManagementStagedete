package com.example.PetgoraBackend.config;

import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.entity.User;
import com.example.PetgoraBackend.entity.petData.VitalSigns;
import com.example.PetgoraBackend.repository.PetRepo;
import com.example.PetgoraBackend.service.petData.VitalSignsService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class VitalSignsWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final Map<Integer, WebSocketSession> userSessions = new HashMap<>();
    private final PetRepo petRepository;
    private final VitalSignsService vitalSignsService;
    private final ObjectMapper mapper;

    // Queue to handle message sending sequentially
    private final ConcurrentLinkedQueue<TextMessage> messageQueue = new ConcurrentLinkedQueue<>();

    public VitalSignsWebSocketHandler(PetRepo petRepository, VitalSignsService vitalSignsService) {
        this.petRepository = petRepository;
        this.vitalSignsService = vitalSignsService;
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule()); // Register the JavaTimeModule
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        Integer userId = getUserIdFromSession(session);
        if (userId != null) {
            userSessions.put(userId, session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        userSessions.values().remove(session);
    }

    private Integer getUserIdFromSession(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query != null && query.contains("userId=")) {
            return Integer.parseInt(query.split("userId=")[1]);
        }
        return null;
    }

    public void sendVitalSignsData(String message) {
        try {
            JsonNode jsonNode = mapper.readTree(message);

            // Extract vital signs data
            JsonNode vitalSignsNode = jsonNode.get("vitalSigns");
            Integer petId = vitalSignsNode.get("petId").asInt();
            Pet pet = petRepository.findById(petId).orElse(null);

            if (pet != null) {
                User owner = pet.getOwner();
                WebSocketSession session = userSessions.get(owner.getId());

                // Save vital signs data to the database
                VitalSigns vitalSigns = new VitalSigns();
                vitalSigns.setPet(pet); // Attach the managed Pet entity
                vitalSigns.setHeartRate(vitalSignsNode.get("heartRate").asText());
                vitalSigns.setTemperature(vitalSignsNode.get("temperature").asText());
                vitalSigns.setActivityLevel(vitalSignsNode.get("activityLevel").asText());
                vitalSigns.setLastUpdated(OffsetDateTime.parse(vitalSignsNode.get("lastUpdated").asText()).toLocalDateTime());
                vitalSigns.setSteps(vitalSignsNode.get("steps").asText());
                vitalSignsService.saveOrUpdateVitalSigns(vitalSigns);

                // Prepare the simplified response
                Map<String, Object> response = new HashMap<>();
                response.put("petId", petId);
                response.put("heartRate", vitalSignsNode.get("heartRate").asText());
                response.put("temperature", vitalSignsNode.get("temperature").asText());
                response.put("activityLevel", vitalSignsNode.get("activityLevel").asText());
                response.put("lastUpdated", vitalSignsNode.get("lastUpdated").asText());
                response.put("steps", vitalSignsNode.get("steps").asText());
                // Send vital signs data via WebSocket
                if (session != null && session.isOpen()) {
                    synchronized (session) {
                        session.sendMessage(new TextMessage(mapper.writeValueAsString(response)));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
