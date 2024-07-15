package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.entity.UserDto;

public interface IUsersManagementService {

    UserDto registerNewUser(UserDto userDto);

}
