package com.example.PetgoraBackend.config;

import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.entity.User;
import com.example.PetgoraBackend.entity.petData.HealthScore;
import com.example.PetgoraBackend.repository.PetRepo;
import com.example.PetgoraBackend.service.petData.HealthScoreService;
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

@Component
public class HealthScoreWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final Map<Integer, WebSocketSession> userSessions = new HashMap<>();
    private final PetRepo petRepository;
    private final HealthScoreService healthScoreService;
    private final ObjectMapper mapper;

    public HealthScoreWebSocketHandler(PetRepo petRepository, HealthScoreService healthScoreService) {
        this.petRepository = petRepository;
        this.healthScoreService = healthScoreService;
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
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

    public void sendHealthScoreData(String message) {
        try {
            JsonNode jsonNode = mapper.readTree(message);

            JsonNode healthScoreNode = jsonNode.get("healthScore");
            Integer petId = healthScoreNode.get("petId").asInt();
            Pet pet = petRepository.findById(petId).orElse(null);

            if (pet != null) {
                User owner = pet.getOwner();
                WebSocketSession session = userSessions.get(owner.getId());

                HealthScore healthScore = new HealthScore();
                healthScore.setPet(pet);
                healthScore.setAvgHealth(healthScoreNode.get("avgHealth").asText());
                healthScore.setActivityTime(healthScoreNode.get("activityTime").asText());
                healthScore.setCalories(healthScoreNode.get("calories").asText());
                healthScore.setHeartRate(healthScoreNode.get("heartRate").asText());
                healthScore.setSpeed(healthScoreNode.get("speed").asText());
                healthScore.setTimestamp(OffsetDateTime.parse(healthScoreNode.get("timestamp").asText()).toLocalDateTime());
                healthScoreService.saveOrUpdateHealthScore(healthScore);

                // Préparer la réponse simplifiée
                Map<String, Object> response = new HashMap<>();
                response.put("petId", petId);
                response.put("avgHealth", healthScoreNode.get("avgHealth").asText());
                response.put("activityTime", healthScoreNode.get("activityTime").asText());
                response.put("calories", healthScoreNode.get("calories").asText());
                response.put("heartRate", healthScoreNode.get("heartRate").asText());
                response.put("speed", healthScoreNode.get("speed").asText());
                response.put("timestamp", healthScoreNode.get("timestamp").asText());

                // Envoyer les données via WebSocket
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
