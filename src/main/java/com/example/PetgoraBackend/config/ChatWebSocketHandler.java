package com.example.PetgoraBackend.config;

import com.example.PetgoraBackend.dto.chat.MessageDTO;
import com.example.PetgoraBackend.entity.User;
import com.example.PetgoraBackend.entity.chat.GroupChat;
import com.example.PetgoraBackend.service.chat.GroupChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Map<Integer, Set<WebSocketSession>> groupChatSessions = new HashMap<>();
    private final Map<Integer, WebSocketSession> userSessions = new HashMap<>();
    private final GroupChatService groupChatService;
    private final ObjectMapper mapper;

    public ChatWebSocketHandler(GroupChatService groupChatService) {
        this.groupChatService = groupChatService;
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Integer userId = getUserIdFromSession(session);
        if (userId != null) {
            userSessions.put(userId, session);

            // Use service method to get all groups with members
            Iterable<GroupChat> allGroups = groupChatService.findAllWithMembers();
            for (GroupChat groupChat : allGroups) {
                Set<User> members = groupChat.getMembers();
                for (User user : members) {
                    if (user.getId().equals(userId)) {
                        groupChatSessions.computeIfAbsent(groupChat.getId(), k -> new HashSet<>()).add(session);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Integer userId = getUserIdFromSession(session);
        if (userId != null) {
            userSessions.remove(userId);
            for (Set<WebSocketSession> sessions : groupChatSessions.values()) {
                sessions.remove(session);
            }
        }
    }

    private Integer getUserIdFromSession(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query != null && query.contains("userId=")) {
            return Integer.parseInt(query.split("userId=")[1]);
        }
        return null;
    }

    private String serializeMessageDTO(MessageDTO messageDTO) throws JsonProcessingException {
        // Convert LocalDateTime to String before serialization
        return mapper.writeValueAsString(messageDTO);
    }

    public void sendMessageToGroup(Integer groupId, MessageDTO messageDTO) {
        Set<WebSocketSession> sessions = groupChatSessions.get(groupId);
        if (sessions != null) {
            try {
                String messageJson = serializeMessageDTO(messageDTO);
                for (WebSocketSession session : sessions) {
                    if (session.isOpen()) {
                        session.sendMessage(new TextMessage(messageJson));
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
