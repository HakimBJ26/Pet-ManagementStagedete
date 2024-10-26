package com.example.PetgoraBackend.controller.chat;


import com.example.PetgoraBackend.dto.chat.MessageDTO;
import com.example.PetgoraBackend.service.chat.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageDTO messageDTO) {
        MessageDTO createdMessage = messageService.createMessage(messageDTO);
        return ResponseEntity.ok(createdMessage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageDTO> updateMessage(@PathVariable Integer id, @RequestBody MessageDTO messageDTO) {
        MessageDTO updatedMessage = messageService.updateMessage(id, messageDTO);
        return ResponseEntity.ok(updatedMessage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Integer id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getMessageById(@PathVariable Integer id) {
        MessageDTO messageDTO = messageService.getMessageById(id);
        return ResponseEntity.ok(messageDTO);
    }

    @GetMapping("/group/{groupChatId}")
    public ResponseEntity<List<MessageDTO>> getAllMessagesByGroupChatId(@PathVariable Integer groupChatId) {
        List<MessageDTO> messages = messageService.getAllMessagesByGroupChatId(groupChatId);
        return ResponseEntity.ok(messages);
    }
}
