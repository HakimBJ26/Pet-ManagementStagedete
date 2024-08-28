package com.example.PetgoraBackend.service.chat;


import com.example.PetgoraBackend.dto.chat.MessageDTO;

import java.util.List;

public interface MessageService {
    MessageDTO createMessage(MessageDTO messageDTO);
    MessageDTO updateMessage(Integer id, MessageDTO messageDTO);
    void deleteMessage(Integer id);
    MessageDTO getMessageById(Integer id);
    List<MessageDTO> getAllMessagesByGroupChatId(Integer groupChatId);
}

