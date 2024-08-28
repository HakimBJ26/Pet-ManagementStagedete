package com.example.PetgoraBackend.entity.chat;


import com.example.PetgoraBackend.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "group_chat_id", referencedColumnName = "id")
    private GroupChat groupChat;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(nullable = false)
    private String text;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private boolean edited = false;

    @Column(name = "sender_name")
    private String senderName;
    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}
