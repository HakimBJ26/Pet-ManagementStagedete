package com.example.PetgoraBackend.config;

import com.example.PetgoraBackend.entity.Device;
import com.example.PetgoraBackend.repository.DeviceRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@EnableScheduling
public class MockServerWebSocketClient {

    private final Map<String, WebSocketClient> deviceWebSocketClients = new HashMap<>();
    private final LocationWebSocketHandler locationWebSocketHandler;
    private final VitalSignsWebSocketHandler vitalSignsWebSocketHandler;
    private final DeviceRepository deviceRepository;

    public MockServerWebSocketClient(LocationWebSocketHandler locationWebSocketHandler,
                                     VitalSignsWebSocketHandler vitalSignsWebSocketHandler,
                                     DeviceRepository deviceRepository) {
        this.locationWebSocketHandler = locationWebSocketHandler;
        this.vitalSignsWebSocketHandler = vitalSignsWebSocketHandler;
        this.deviceRepository = deviceRepository;
    }

    @PostConstruct
    public void start() throws URISyntaxException {
        refreshConnections();
    }

    @Scheduled(fixedDelay = 6000) // Refresh every 6 seconds
    public void refreshConnections() {
        List<Device> activeDevices = deviceRepository.findByIsActive(true);
        System.out.println("Active devices: " + activeDevices.stream().map(Device::getId).toList());
        Map<String, WebSocketClient> newDeviceWebSocketClients = new HashMap<>();

        // Connect to new active devices
        for (Device device : activeDevices) {
            String deviceId = device.getId().toString();
            WebSocketClient existingClient = deviceWebSocketClients.get(deviceId);

            if (existingClient == null || !existingClient.isOpen()) {
                WebSocketClient webSocketClient = createWebSocketClient(deviceId);
                newDeviceWebSocketClients.put(deviceId, webSocketClient);
                webSocketClient.connect(); // Initiate connection immediately
                System.out.println("Attempting to connect to device " + deviceId);
            } else {
                newDeviceWebSocketClients.put(deviceId, existingClient);
            }
        }

        // Close connections for devices that are no longer active
        for (String deviceId : deviceWebSocketClients.keySet()) {
            if (!newDeviceWebSocketClients.containsKey(deviceId)) {
                WebSocketClient client = deviceWebSocketClients.get(deviceId);
                if (client != null) {
                    System.out.println("Closing inactive connection for device " + deviceId);
                    client.close();
                }
            }
        }

        // Update the map with the new WebSocket clients
        deviceWebSocketClients.clear();
        deviceWebSocketClients.putAll(newDeviceWebSocketClients);
    }

    private WebSocketClient createWebSocketClient(String deviceId) {
        return new WebSocketClient(URI.create("ws://localhost:4000/device" + deviceId)) {
            private boolean tokenVerified = false;

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("Connected to WebSocket for device " + deviceId);
            }

            @Override
            public void onMessage(String message) {
                System.out.println("Received message from device " + deviceId + ": " + message);
                try {
                    if (!tokenVerified) {
                        String receivedToken = extractToken(message);

                        if (receivedToken == null) {
                            System.out.println("Token not found in message for device " + deviceId + ". Closing connection.");
                            this.close();
                            return;
                        }

                        // Load the current token from the database
                        Device device = deviceRepository.findById(Long.parseLong(deviceId)).orElse(null);

                        if (device == null) {
                            System.out.println("Device not found for ID " + deviceId);
                            this.close();
                            return;
                        }

                        String currentToken = device.getToken();

                        if (currentToken.equals(receivedToken)) {
                            tokenVerified = true;
                            System.out.println("Token verified for device " + deviceId);
                        } else {
                            System.out.println("Invalid token for device " + deviceId + ": " + receivedToken);
                            this.close();
                            return;
                        }
                    }

                    if (tokenVerified) {
                        locationWebSocketHandler.sendLocationData(message);
                        vitalSignsWebSocketHandler.sendVitalSignsData(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("WebSocket connection closed for device " + deviceId + ". Reason: " + reason);
                markDeviceAsInactive(deviceId); // Mark the device as inactive in the database
                deviceWebSocketClients.remove(deviceId); // Remove the device from the map
            }

            @Override
            public void onError(Exception ex) {
                System.out.println("Error for device " + deviceId + ": " + ex.getMessage());
                markDeviceAsInactive(deviceId); // Mark the device as inactive in the database
                deviceWebSocketClients.remove(deviceId); // Remove the device from the map
            }

            private String extractToken(String message) {
                try {
                    JsonNode jsonNode = new ObjectMapper().readTree(message);
                    JsonNode tokenNode = jsonNode.get("token");
                    return tokenNode != null ? tokenNode.asText() : null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            private void markDeviceAsInactive(String deviceId) {
                // Mark the device as inactive in the database
                Device device = deviceRepository.findById(Long.parseLong(deviceId)).orElse(null);
                if (device != null) {
                    device.setActive(false);
                    deviceRepository.save(device);
                    System.out.println("Device " + deviceId + " marked as inactive.");
                }
            }
        };
    }

    @PreDestroy
    public void stop() {
        System.out.println("Shutting down WebSocket clients.");
        deviceWebSocketClients.values().forEach(WebSocketClient::close);
    }
}
