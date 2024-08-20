package com.example.PetgoraBackend.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final LocationWebSocketHandler locationWebSocketHandler;
    private final VitalSignsWebSocketHandler vitalSignsWebSocketHandler;
    private final ActivityWebSocketHandler activityWebSocketHandler;

    public WebSocketConfig(LocationWebSocketHandler locationWebSocketHandler, VitalSignsWebSocketHandler vitalSignsWebSocketHandler, ActivityWebSocketHandler activityWebSocketHandler) {
        this.locationWebSocketHandler = locationWebSocketHandler;
        this.vitalSignsWebSocketHandler = vitalSignsWebSocketHandler;
        this.activityWebSocketHandler = activityWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(locationWebSocketHandler, "/ws/location").setAllowedOrigins("*");
        registry.addHandler(vitalSignsWebSocketHandler, "/ws/vital_signs").setAllowedOrigins("*");
        registry.addHandler(activityWebSocketHandler, "/ws/activity").setAllowedOrigins("*");
    }


}
