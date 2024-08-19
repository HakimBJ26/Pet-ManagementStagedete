/*package com.example.PetgoraBackend.config;
import com.example.PetgoraBackend.dto.petData.HealthScoreDto;
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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class HealthScoreWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final Map<Integer, WebSocketSession> userSessions = new HashMap<>();
    private final PetRepo petRepository;
    private final HealthScoreService healthScoreService;
    private final ObjectMapper mapper;

    // Queue to handle message sending sequentially
    private final ConcurrentLinkedQueue<TextMessage> messageQueue = new ConcurrentLinkedQueue<>();

    public HealthScoreWebSocketHandler(PetRepo petRepository, HealthScoreService healthScoreService) {
        this.petRepository = petRepository;
        this.healthScoreService = healthScoreService;
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

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();
        JsonNode jsonNode = mapper.readTree(payload);

        Long petId = jsonNode.get("petId").asLong();
        HealthScoreDto healthScoreDto = parseHealthScoreDto(jsonNode);

        // Save or update the health score
        healthScoreService.saveOrUpdateHealthScore(healthScoreDto);


        TextMessage acknowledgment = new TextMessage("HealthScore updated for Pet ID: " + petId);
        session.sendMessage(acknowledgment);
    }

    private HealthScoreDto parseHealthScoreDto(JsonNode jsonNode) {
        HealthScoreDto healthScoreDto = new HealthScoreDto();
        healthScoreDto.setPetId(jsonNode.get("petId").asInt());
        healthScoreDto.setAvgHealth(jsonNode.get("avgHealth").asText());
        healthScoreDto.setActivityTime(jsonNode.get("activityTime").asText());
        healthScoreDto.setCalories(jsonNode.get("calories").asText());
        healthScoreDto.setHeartRate(jsonNode.get("heartRate").asText());
        healthScoreDto.setSpeed(jsonNode.get("speed").asText());
        healthScoreDto.setTimestamp(LocalDateTime.parse(jsonNode.get("timestamp").asText()));
        return healthScoreDto;
    }

    private Integer getUserIdFromSession(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query != null && query.contains("userId=")) {
            return Integer.parseInt(query.split("userId=")[1]);
        }
        return null;
    }
}*/
