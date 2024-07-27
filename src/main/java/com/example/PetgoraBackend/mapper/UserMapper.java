package com.example.PetgoraBackend.mapper;

import com.example.PetgoraBackend.entity.User;
import com.example.PetgoraBackend.dto.UserDto;
import com.example.PetgoraBackend.dto.UserLoginDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(target = "password", ignore = true)
    UserDto toUserDto(User user);

    User toUser(UserDto userModel);

    User toUser(UserLoginDto userLoginDto);
}
