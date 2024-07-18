package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.entity.UserDto;
import com.example.PetgoraBackend.entity.UserLoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUsersManagementService {

    UserDto registerNewUser(UserDto userDto);

    ResponseEntity<UserDto> UserLogin(UserLoginDto userLoginDto, HttpServletResponse response);

    UserDto updateUserProfile(UserDto UserDto);
    ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<String>deleteUserById(Long userId);

     ResponseEntity<String> updateUserByAdmin(Long userId, UserDto userDto);

    List<UserDto> getAllUsers();

    UserDto getUserById(Long userId);
     void logout(HttpServletResponse response);
    public UserDto getUserProfile();
}
