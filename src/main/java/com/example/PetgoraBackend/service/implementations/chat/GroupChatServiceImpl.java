package com.example.PetgoraBackend.service.implementations.chat;

import com.example.PetgoraBackend.dto.chat.GroupChatDTO;
import com.example.PetgoraBackend.entity.User;
import com.example.PetgoraBackend.entity.chat.GroupChat;
import com.example.PetgoraBackend.mapper.chat.GroupChatMapper;
import com.example.PetgoraBackend.repository.chat.GroupChatRepository;
import com.example.PetgoraBackend.repository.UsersRepo;
import com.example.PetgoraBackend.repository.chat.MessageRepository;
import com.example.PetgoraBackend.service.chat.GroupChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupChatServiceImpl implements GroupChatService {

    @Autowired
    private GroupChatRepository groupChatRepository;

    @Autowired
    private UsersRepo userRepository;

    @Autowired
    private GroupChatMapper groupChatMapper;

    @Autowired
    private MessageRepository messageRepository ;
    @Override
    public List<GroupChatDTO> getAllGroupChats() {
        List<GroupChat> groupChats = groupChatRepository.findAll();
        return groupChats.stream()
                .map(groupChat -> groupChatMapper.toDTO(groupChat))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GroupChat> findAllWithMembers() {
        List<GroupChat> allGroups = groupChatRepository.findAll();

        // Explicitly load members to avoid LazyInitializationException
        for (GroupChat groupChat : allGroups) {
            groupChat.getMembers().size();  // Forces Hibernate to initialize the collection
        }
        return allGroups;
    }

    @Override
    public GroupChatDTO getGroupChatById(Integer id) {
        GroupChat groupChat = groupChatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GroupChat not found with id " + id));
        return groupChatMapper.toDTO(groupChat);
    }

    @Override
    public GroupChatDTO createGroupChat(GroupChatDTO groupChatDTO) {
        GroupChat groupChat = groupChatMapper.toEntity(groupChatDTO);
        groupChat = groupChatRepository.save(groupChat);
        return groupChatMapper.toDTO(groupChat);
    }

    @Override
    public GroupChatDTO updateGroupChat(Integer id, GroupChatDTO groupChatDTO) {
        GroupChat existingGroupChat = groupChatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GroupChat not found with id " + id));

        existingGroupChat.setName(groupChatDTO.getName());
        existingGroupChat.setMembers(groupChatMapper.mapIdsToUsers(groupChatDTO.getMemberIds(), userRepository));

        // Save the updated group chat
        existingGroupChat = groupChatRepository.save(existingGroupChat);
        // Convert the updated entity back to DTO and return
        return groupChatMapper.toDTO(existingGroupChat);
    }

    @Override
    @Transactional
    public void deleteGroupChat(Integer id) {
        GroupChat groupChat = groupChatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GroupChat not found with id " + id));

        messageRepository.deleteByGroupChatId(groupChat.getId());

        groupChatRepository.delete(groupChat);
    }



    @Override
    public Set<GroupChatDTO> getGroupChatsByUserId(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Set<GroupChat> groupChats = groupChatRepository.findByMembers(user);
        return groupChats.stream().map(groupChatMapper::toDTO).collect(Collectors.toSet());
    }

     @Override
    public GroupChatDTO addUserToGroupChat(Integer groupId, Integer userId) {
        GroupChat groupChat = groupChatRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group chat not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        groupChat.getMembers().add(user);
        GroupChat updatedGroupChat = groupChatRepository.save(groupChat);

        return groupChatMapper.toDTO(updatedGroupChat);
    }

    @Override
    public GroupChatDTO removeUserFromGroupChat(Integer groupId, Integer userId) {
        GroupChat groupChat = groupChatRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group chat not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        groupChat.getMembers().remove(user);
        GroupChat updatedGroupChat = groupChatRepository.save(groupChat);

        return groupChatMapper.toDTO(updatedGroupChat);
    }
}
