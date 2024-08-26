package com.example.PetgoraBackend.config;

import com.example.PetgoraBackend.dto.alerts.HealthAlertDTO;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.entity.User;
import com.example.PetgoraBackend.entity.alerts.NotificationTimestamps;
import com.example.PetgoraBackend.entity.petData.VitalSigns;
import com.example.PetgoraBackend.repository.PetRepo;
import com.example.PetgoraBackend.repository.alerts.NotificationTimestampsRepository;
import com.example.PetgoraBackend.service.alerts.HealthAlertService;
import com.example.PetgoraBackend.service.notif.FirebaseNotificationService;
import com.example.PetgoraBackend.service.petData.VitalSignsService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class VitalSignsWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final Map<Integer, WebSocketSession> userSessions = new HashMap<>();
    private final PetRepo petRepository;
    private final VitalSignsService vitalSignsService;
    private final FirebaseNotificationService firebaseNotificationService;
    private final ObjectMapper mapper;
    private final HealthAlertService healthAlertService;
    private final NotificationTimestampsRepository notificationTimestampsRepository;  // Add the repository

    public VitalSignsWebSocketHandler(PetRepo petRepository, VitalSignsService vitalSignsService,
                                      FirebaseNotificationService firebaseNotificationService, HealthAlertService healthAlertService,
                                      NotificationTimestampsRepository notificationTimestampsRepository) {  // Inject the repository
        this.petRepository = petRepository;
        this.vitalSignsService = vitalSignsService;
        this.firebaseNotificationService = firebaseNotificationService;
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule()); // Register the JavaTimeModule
        this.healthAlertService = healthAlertService;
        this.notificationTimestampsRepository = notificationTimestampsRepository;  // Initialize the repository
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        Integer userId = getUserIdFromSession(session);
        if (userId != null) {
            userSessions.put(userId, session);
        }
        return ;
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

            JsonNode vitalSignsNode = jsonNode.get("vitalSigns");
            Integer petId = vitalSignsNode.get("petId").asInt();
            Pet pet = petRepository.findById(petId).orElse(null);

            if (pet != null) {
                User owner = pet.getOwner();
                WebSocketSession session = userSessions.get(owner.getId());

                VitalSigns vitalSigns = new VitalSigns();
                vitalSigns.setPet(pet);
                vitalSigns.setHeartRate(vitalSignsNode.get("heartRate").asText());
                vitalSigns.setTemperature(vitalSignsNode.get("temperature").asText());
                vitalSigns.setActivityLevel(vitalSignsNode.get("activityLevel").asText());
                vitalSigns.setLastUpdated(OffsetDateTime.parse(vitalSignsNode.get("lastUpdated").asText()).toLocalDateTime());
                vitalSignsService.saveOrUpdateVitalSigns(vitalSigns);

                // Prepare the simplified response
                Map<String, Object> response = new HashMap<>();
                response.put("petId", petId);
                response.put("heartRate", vitalSignsNode.get("heartRate").asText());
                response.put("temperature", vitalSignsNode.get("temperature").asText());
                response.put("activityLevel", vitalSignsNode.get("activityLevel").asText());
                response.put("lastUpdated", vitalSignsNode.get("lastUpdated").asText());
                // Send vital signs data via WebSocket
                if (session != null && session.isOpen()) {
                    synchronized (session) {
                        session.sendMessage(new TextMessage(mapper.writeValueAsString(response)));
                    }
                }

                String token = owner.getMessagingToken();
                if (token != null) {
                    token = token.replace("\"", "").trim();
                    int heartRate = vitalSignsNode.get("heartRate").asInt();
                    double temperature = vitalSignsNode.get("temperature").asDouble();
                    String activityLevel = vitalSignsNode.get("activityLevel").asText();

                    // High heart rate notification
                    if (heartRate >= 90) {
                        sendHealthAlert(owner, pet, token, heartRate, "High Heart Rate", "High", "consult a veterinarian immediately", "lastHeartRateNotification");
                    }

                    // Low temperature notification
                    if (temperature <= 38.0) {
                        sendHealthAlert(owner, pet, token, temperature, "Low Temperature", "Medium", "keep your pet warm and consult a vet if needed", "lastTemperatureNotification");
                    }

                    // Unusual activity level notification
                    if (activityLevel.equals("Low")) {
                        sendHealthAlert(owner, pet, token, activityLevel, "Low Activity Level", "Medium", "ensure your pet is active or consult a vet", "lastActivityLevelNotification");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendHealthAlert(User owner, Pet pet, String token, Object value, String alertType, String severity, String action, String notificationField) throws Exception {
        NotificationTimestamps timestamps = pet.getNotificationTimestamps();
        if (timestamps == null) {
            timestamps = new NotificationTimestamps();
            timestamps.setPet(pet);
            pet.setNotificationTimestamps(timestamps);
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastNotificationTime = getLastNotificationTime(timestamps, notificationField);

        if (lastNotificationTime == null || lastNotificationTime.plusMinutes(10).isBefore(now)) {
            String title = alertType + " noticed for " + pet.getName() + ": " + value;
            String body = "Hello " + owner.getName() + ", check your pet's vital signs!";
            firebaseNotificationService.sendNotification(token, title, body);

            HealthAlertDTO healthAlertDTO = new HealthAlertDTO();
            healthAlertDTO.setAction(action);
            healthAlertDTO.setSeverity(severity);
            healthAlertDTO.setTitle(alertType + " Alert: " + value);
            healthAlertDTO.setPetId(pet.getId());
            healthAlertService.saveHealthAlert(healthAlertDTO);

            // Update the timestamp
            updateNotificationTime(timestamps, notificationField, now);
            notificationTimestampsRepository.save(timestamps);
        }
    }

    private LocalDateTime getLastNotificationTime(NotificationTimestamps timestamps, String notificationField) {
        switch (notificationField) {
            case "lastHeartRateNotification":
                return timestamps.getLastHeartRateNotification();
            case "lastTemperatureNotification":
                return timestamps.getLastTemperatureNotification();
            case "lastActivityLevelNotification":
                return timestamps.getLastActivityLevelNotification();
            default:
                return null;
        }
    }

    private void updateNotificationTime(NotificationTimestamps timestamps, String notificationField, LocalDateTime now) {
        switch (notificationField) {
            case "lastHeartRateNotification":
                timestamps.setLastHeartRateNotification(now);
                break;
            case "lastTemperatureNotification":
                timestamps.setLastTemperatureNotification(now);
                break;
            case "lastActivityLevelNotification":
                timestamps.setLastActivityLevelNotification(now);
                break;
        }
    }
}
