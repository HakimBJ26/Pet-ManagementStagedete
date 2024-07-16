package com.example.PetgoraBackend.mapper;

import com.example.PetgoraBackend.entity.User;
import com.example.PetgoraBackend.entity.UserDto;
import com.example.PetgoraBackend.entity.UserLoginDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);

    User toUser(UserDto userModel);

    User toUser(UserLoginDto userLoginDto);
}
