package com.example.PetgoraBackend.entity.chat;


import com.example.PetgoraBackend.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "group_chat")
@Data
public class GroupChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToMany
    @JoinTable(
            name = "group_chat_members",
            joinColumns = @JoinColumn(name = "group_chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )

    private Set<User> members = new HashSet<>();

    @OneToMany(mappedBy = "groupChat", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Message> messages = new HashSet<>();
}
