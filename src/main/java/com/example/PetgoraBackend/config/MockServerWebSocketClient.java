package com.example.PetgoraBackend.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class MockServerWebSocketClient {

    private WebSocketClient locationWebSocketClient;
    private WebSocketClient vitalSignsWebSocketClient;
    private final LocationWebSocketHandler locationWebSocketHandler;
    private final VitalSignsWebSocketHandler vitalSignsWebSocketHandler;

    public MockServerWebSocketClient(LocationWebSocketHandler locationWebSocketHandler, VitalSignsWebSocketHandler vitalSignsWebSocketHandler) {
        this.locationWebSocketHandler = locationWebSocketHandler;
        this.vitalSignsWebSocketHandler = vitalSignsWebSocketHandler;
    }

    @PostConstruct
    public void start() throws URISyntaxException {
        // WebSocket Client for Location Data
        locationWebSocketClient = new WebSocketClient(new URI("ws://localhost:4000")) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("Connected to mock server WebSocket for Location");
            }

            @Override
            public void onMessage(String message) {
                System.out.println("Received Location message: " + message);
                try {
                    locationWebSocketHandler.sendLocationData(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("Location WebSocket connection closed");
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        };
        locationWebSocketClient.connect();

        // WebSocket Client for Vital Signs Data
        vitalSignsWebSocketClient = new WebSocketClient(new URI("ws://localhost:4000")) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("Connected to mock server WebSocket for Vital Signs");
            }

            @Override
            public void onMessage(String message) {
                System.out.println("Received Vital Signs message: " + message);
                try {
                    vitalSignsWebSocketHandler.sendVitalSignsData(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("Vital Signs WebSocket connection closed");
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        };
        vitalSignsWebSocketClient.connect();
    }

    @PreDestroy
    public void stop() {
        if (locationWebSocketClient != null) {
            locationWebSocketClient.close();
        }
        if (vitalSignsWebSocketClient != null) {
            vitalSignsWebSocketClient.close();
        }
    }
}
