package com.example.PetgoraBackend.mapper.chat;

import com.example.PetgoraBackend.dto.chat.GroupChatDTO;
import com.example.PetgoraBackend.entity.chat.GroupChat;
import com.example.PetgoraBackend.entity.User;
import com.example.PetgoraBackend.repository.UsersRepo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = UsersRepo.class)
public abstract class GroupChatMapper {

    @Autowired
    protected UsersRepo userRepository;

    @Mapping(target = "createdBy", expression = "java(userRepository.findById(groupChatDTO.getCreatedById()).orElse(null))")
    @Mapping(target = "members", expression = "java(mapIdsToUsers(groupChatDTO.getMemberIds(), userRepository))")
    public abstract GroupChat toEntity(GroupChatDTO groupChatDTO);

    @Mapping(target = "createdById", source = "createdBy.id")
    @Mapping(target = "memberIds", source = "members")
    public abstract GroupChatDTO toDTO(GroupChat groupChat);

    public Set<User> mapIdsToUsers(Set<Integer> ids, UsersRepo userRepository) {
        return ids.stream()
                .map(id -> userRepository.findById(id).orElse(null))
                .collect(Collectors.toSet());
    }

    public Set<Integer> mapUsersToIds(Set<User> users) {
        return users.stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }
}

