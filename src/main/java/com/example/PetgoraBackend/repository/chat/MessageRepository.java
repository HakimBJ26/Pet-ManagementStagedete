package com.example.PetgoraBackend.repository.chat;


import com.example.PetgoraBackend.entity.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByGroupChatId(Integer groupChatId);
    void deleteByGroupChatId(Integer groupChatId);


}
