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

    private final ChatWebSocketHandler chatWebSocketHandler;

    private final HealthScoreWebSocketHandler healthScoreWebSocketHandler;


    public WebSocketConfig(LocationWebSocketHandler locationWebSocketHandler, VitalSignsWebSocketHandler vitalSignsWebSocketHandler, ActivityWebSocketHandler activityWebSocketHandler, ChatWebSocketHandler chatWebSocketHandler,HealthScoreWebSocketHandler healthScoreWebSocketHandler) {
        this.locationWebSocketHandler = locationWebSocketHandler;
        this.vitalSignsWebSocketHandler = vitalSignsWebSocketHandler;
        this.activityWebSocketHandler = activityWebSocketHandler;
        this.chatWebSocketHandler = chatWebSocketHandler ;
        this.healthScoreWebSocketHandler = healthScoreWebSocketHandler;

    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(locationWebSocketHandler, "/ws/location").setAllowedOrigins("*");
        registry.addHandler(vitalSignsWebSocketHandler, "/ws/vital_signs").setAllowedOrigins("*");
        registry.addHandler(activityWebSocketHandler, "/ws/activity").setAllowedOrigins("*");

        registry.addHandler(chatWebSocketHandler, "/ws/chat").setAllowedOrigins("*");

        registry.addHandler(healthScoreWebSocketHandler, "/ws/health_score").setAllowedOrigins("*");



    }


}
