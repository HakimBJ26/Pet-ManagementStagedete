package com.example.PetgoraBackend.mapper.chat;


import com.example.PetgoraBackend.dto.chat.MessageDTO;
import com.example.PetgoraBackend.entity.chat.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mapping(source = "groupChat.id", target = "groupId")
    @Mapping(source = "sender.id", target = "senderId")
    MessageDTO toDTO(Message message);

    @Mapping(source = "groupId", target = "groupChat.id")
    @Mapping(source = "senderId", target = "sender.id")
    Message toEntity(MessageDTO messageDTO);
}
