package com.example.PetgoraBackend.config;

import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.entity.User;
import com.example.PetgoraBackend.entity.petData.Activity;
import com.example.PetgoraBackend.repository.PetRepo;
import com.example.PetgoraBackend.service.petData.ActivityService;
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
public class ActivityWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final Map<Integer, WebSocketSession> userSessions = new HashMap<>();
    private final PetRepo petRepository;
    private final ActivityService activityService;
    private final ObjectMapper mapper;

    // Queue to handle message sending sequentially
    private final ConcurrentLinkedQueue<TextMessage> messageQueue = new ConcurrentLinkedQueue<>();

    public ActivityWebSocketHandler(PetRepo petRepository, ActivityService activityService) {
        this.petRepository = petRepository;
        this.activityService = activityService;
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

    public void sendActivityData(String message) {
        try {
            JsonNode jsonNode = mapper.readTree(message);

            // Extract activity data
            JsonNode activityNode = jsonNode.get("activity");
            Integer petId = activityNode.get("petId").asInt();
            Pet pet = petRepository.findById(petId).orElse(null);

            if (pet != null) {
                User owner = pet.getOwner();
                WebSocketSession session = userSessions.get(owner.getId());

                // Save activity data to the database
                Activity activity = new Activity();
                activity.setPet(pet); // Attach the managed Pet entity
                activity.setHeartRate(activityNode.get("heartRate").asText());
                activity.setSteps(activityNode.get("steps").asText());
                activity.setAvergeBurn(activityNode.get("averageBurn").asText());
                activity.setHealthScore(activityNode.get("healthScore").asText());
                activity.setTimeSpentInActivity(activityNode.get("timeSpentInActivity").asText());
                activity.setLastUpdated(OffsetDateTime.parse(activityNode.get("lastUpdated").asText()).toLocalDateTime());
                activityService.saveOrUpdateActivity(activity);

                // Prepare the simplified response
                Map<String, Object> response = new HashMap<>();
                response.put("petId", petId);
                response.put("heartRate", activityNode.get("heartRate").asText());
                response.put("steps", activityNode.get("steps").asText());
                response.put("averageBurn", activityNode.get("averageBurn").asText());
                response.put("healthScore", activityNode.get("healthScore").asText());
                response.put("timeSpentInActivity", activityNode.get("timeSpentInActivity").asText());
                response.put("lastUpdated", activityNode.get("lastUpdated").asText());

                // Send activity data via WebSocket
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
