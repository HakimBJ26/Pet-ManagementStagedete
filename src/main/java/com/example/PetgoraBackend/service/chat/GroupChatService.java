package com.example.PetgoraBackend.service.chat;


import com.example.PetgoraBackend.dto.chat.GroupChatDTO;
import com.example.PetgoraBackend.entity.chat.GroupChat;

import java.util.List;
import java.util.Set;

public interface GroupChatService {
    GroupChatDTO createGroupChat(GroupChatDTO groupChatDTO);
    GroupChatDTO updateGroupChat(Integer id, GroupChatDTO groupChatDTO);
    void deleteGroupChat(Integer id);
    GroupChatDTO getGroupChatById(Integer id);
    List<GroupChatDTO> getAllGroupChats();
    Set<GroupChatDTO> getGroupChatsByUserId(Integer userId);
    public GroupChatDTO addUserToGroupChat(Integer groupId, Integer userId) ;
    public GroupChatDTO removeUserFromGroupChat(Integer groupId, Integer userId) ;

    public List<GroupChat> findAllWithMembers() ;

    }
