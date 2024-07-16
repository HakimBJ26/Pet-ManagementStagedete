package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.entity.UserDto;
import com.example.PetgoraBackend.entity.UserLoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface IUsersManagementService {

    UserDto registerNewUser(UserDto userDto);


    ResponseEntity<UserDto> UserLogin(UserLoginDto userLoginDto, HttpServletResponse response);

     UserDto updateUserProfile(UserDto UserDto);

    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response);


}
