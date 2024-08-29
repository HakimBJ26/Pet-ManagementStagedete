package com.example.PetgoraBackend.service.implementations.chat;


import com.example.PetgoraBackend.config.ChatWebSocketHandler;
import com.example.PetgoraBackend.dto.chat.MessageDTO;
import com.example.PetgoraBackend.entity.chat.Message;
import com.example.PetgoraBackend.mapper.chat.MessageMapper;
import com.example.PetgoraBackend.repository.chat.MessageRepository;
import com.example.PetgoraBackend.service.chat.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;


    private final ChatWebSocketHandler chatWebSocketHandler;

    public MessageServiceImpl(ChatWebSocketHandler chatWebSocketHandler) {
        this.chatWebSocketHandler = chatWebSocketHandler;
    }

    @Override
    public MessageDTO createMessage(MessageDTO messageDTO) {
        Message message = MessageMapper.INSTANCE.toEntity(messageDTO);
        message = messageRepository.save(message);
        chatWebSocketHandler.sendMessageToGroup(message.getGroupChat().getId(), messageDTO);
        return MessageMapper.INSTANCE.toDTO(message);
    }

    @Override
    public MessageDTO updateMessage(Integer id, MessageDTO messageDTO) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        message.setText(messageDTO.getText());
        message.setPhotoUrl(messageDTO.getPhotoUrl());
        message.setEdited(true);

        message = messageRepository.save(message);
        return MessageMapper.INSTANCE.toDTO(message);
    }

    @Override
    public void deleteMessage(Integer id) {
        messageRepository.deleteById(id);
    }

    @Override
    public MessageDTO getMessageById(Integer id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        return MessageMapper.INSTANCE.toDTO(message);
    }

    @Override
    public List<MessageDTO> getAllMessagesByGroupChatId(Integer groupChatId) {
        return messageRepository.findByGroupChatId(groupChatId).stream()
                .map(MessageMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }
}
