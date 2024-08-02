package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.dto.UserDto;
import com.example.PetgoraBackend.dto.UserLoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUsersManagementService {

    UserDto registerNewUser(UserDto userDto);

    ResponseEntity<UserDto> UserLogin(UserLoginDto userLoginDto, HttpServletResponse response);

    UserDto updateUserProfile(UserDto userDto, HttpServletResponse response);

    ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<String> deleteUserById(int userId);

    ResponseEntity<String> updateUserByAdmin(int userId, UserDto userDto);

    List<UserDto> getAllUsers();

    UserDto getUserById(int userId);

    void logout(HttpServletResponse response);

    UserDto getUserProfile();


    void approveUserByEmail(String email);
     List<UserDto> getUnapprovedUsers() ;


    ResponseEntity<String> sendResetPasswordEmail(String email);

    ResponseEntity<String> verifyResetPasswordToken(String token, HttpServletResponse response);
    ResponseEntity<String> resetPassword(String newPassword, String confirmPassword, String token, HttpServletResponse response);

    List<UserDto> searchVeterinariansByName(String name);

}


