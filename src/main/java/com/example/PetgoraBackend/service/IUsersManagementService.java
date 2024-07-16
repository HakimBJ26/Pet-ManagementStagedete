package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.entity.UserDto;
import com.example.PetgoraBackend.entity.UserLoginDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface IUsersManagementService {

    UserDto registerNewUser(UserDto userDto);

  //  UserDto UserLogin (UserLoginDto UserLoginDto);
     ResponseEntity<UserDto> UserLogin(UserLoginDto userLoginDto, HttpServletResponse response);


    }
