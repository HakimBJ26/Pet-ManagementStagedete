package com.example.PetgoraBackend.config;

import com.example.PetgoraBackend.entity.LocationData;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.entity.User;
import com.example.PetgoraBackend.repository.PetRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class LocationWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final Map<Integer, WebSocketSession> userSessions = new HashMap<>();
    private final PetRepo petRepository;
    private final ObjectMapper mapper;

    public LocationWebSocketHandler(PetRepo petRepository) {
        this.petRepository = petRepository;
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

    public void sendLocationData(String message) {
        try {
            JsonNode jsonNode = mapper.readTree(message);

            // Extract location data
            JsonNode locationDataNode = jsonNode.get("locationData");
            LocationData locationData = new LocationData();
            locationData.setPetId(locationDataNode.get("petId").asInt());
            locationData.setLatitude(locationDataNode.get("latitude").asDouble());
            locationData.setLongitude(locationDataNode.get("longitude").asDouble());

            Pet pet = petRepository.findById(locationData.getPetId()).orElse(null);

            if (pet != null) {
                User owner = pet.getOwner();
                WebSocketSession session = userSessions.get(owner.getId());

                // Send location data via WebSocket
                if (session != null && session.isOpen()) {
                    session.sendMessage(new TextMessage(mapper.writeValueAsString(locationData)));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
