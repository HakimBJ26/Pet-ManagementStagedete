package com.example.PetgoraBackend.repository.chat;


import com.example.PetgoraBackend.entity.User;
import com.example.PetgoraBackend.entity.chat.GroupChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GroupChatRepository extends JpaRepository<GroupChat, Integer> {
    Set<GroupChat> findByMembers(User member);
    Set<GroupChat> findByMembers_Id(Integer userId);

}

