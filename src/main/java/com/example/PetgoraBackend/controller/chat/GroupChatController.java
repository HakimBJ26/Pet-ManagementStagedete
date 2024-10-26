package com.example.PetgoraBackend.controller.chat;

import com.example.PetgoraBackend.dto.chat.GroupChatDTO;
import com.example.PetgoraBackend.service.chat.GroupChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/group-chats")
public class GroupChatController {

    @Autowired
    private GroupChatService groupChatService;

    @PostMapping
    public ResponseEntity<GroupChatDTO> createGroupChat(@RequestBody GroupChatDTO groupChatDTO) {
        GroupChatDTO createdGroupChat = groupChatService.createGroupChat(groupChatDTO);
        return ResponseEntity.ok(createdGroupChat);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupChatDTO> updateGroupChat(@PathVariable Integer id, @RequestBody GroupChatDTO groupChatDTO) {
        GroupChatDTO updatedGroupChat = groupChatService.updateGroupChat(id, groupChatDTO);
        return ResponseEntity.ok(updatedGroupChat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupChat(@PathVariable Integer id) {
        groupChatService.deleteGroupChat(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupChatDTO> getGroupChatById(@PathVariable Integer id) {
        GroupChatDTO groupChatDTO = groupChatService.getGroupChatById(id);
        return ResponseEntity.ok(groupChatDTO);
    }

    @GetMapping
    public ResponseEntity<List<GroupChatDTO>> getAllGroupChats() {
        List<GroupChatDTO> groupChats = groupChatService.getAllGroupChats();
        return ResponseEntity.ok(groupChats);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Set<GroupChatDTO>> getGroupChatsByUserId(@PathVariable Integer userId) {
        Set<GroupChatDTO> groupChats = groupChatService.getGroupChatsByUserId(userId);
        return ResponseEntity.ok(groupChats);
    }

    @PostMapping("/join/{userId}/{groupId}")
    public ResponseEntity<GroupChatDTO> joinGroupChat(@PathVariable Integer groupId, @PathVariable Integer userId) {
        GroupChatDTO updatedGroupChat = groupChatService.addUserToGroupChat(groupId, userId);
        return ResponseEntity.ok(updatedGroupChat);
    }

    @PostMapping("/quit/{userId}/{groupId}")
    public ResponseEntity<GroupChatDTO> quitGroupChat(@PathVariable Integer groupId, @PathVariable Integer userId) {
        GroupChatDTO updatedGroupChat = groupChatService.removeUserFromGroupChat(groupId, userId);
        return ResponseEntity.ok(updatedGroupChat);
    }
}
