package com.example.PetgoraBackend.service.notif;

import com.example.PetgoraBackend.repository.PetRepo;
import com.example.PetgoraBackend.repository.UsersRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.entity.User;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirebaseNotificationService {

    @Autowired
    private PetRepo petRepository;

    @Autowired
    private UsersRepo userRepository;



    public void sendNotification(String token, String title, String body) throws Exception {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("FCM token must not be null or empty.");
        }

        Message message = Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();
        try {
            System.out.println("Attempting to send message...");
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (FirebaseMessagingException e) {
            System.err.println("Failed to send message: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
