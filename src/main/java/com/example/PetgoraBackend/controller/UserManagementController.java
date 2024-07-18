package com.example.PetgoraBackend.controller;

import com.example.PetgoraBackend.entity.UserDto;
import com.example.PetgoraBackend.entity.UserLoginDto;
import com.example.PetgoraBackend.service.IUsersManagementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/auth")
public class UserManagementController {
    private final IUsersManagementService usersManagementService;

    public UserManagementController(IUsersManagementService usersManagementService) {
        this.usersManagementService = usersManagementService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(usersManagementService.registerNewUser(userDto));
    }


    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserLoginDto userLoginDto, HttpServletResponse response) {
        return usersManagementService.UserLogin(userLoginDto, response);
    }

    @PutMapping("updateProfile")
    public UserDto updateUserProfile(@RequestBody UserDto userDto) {
        return usersManagementService.updateUserProfile(userDto);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return usersManagementService.refreshToken(request, response);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        return usersManagementService.deleteUserById(userId);
    }


    @GetMapping("/GetAllUsers")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = usersManagementService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/updateUserByAdmin/{userId}")
    public ResponseEntity<String> updateUserByAdmin(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return usersManagementService.updateUserByAdmin(userId, userDto);
    }


    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(usersManagementService.getUserById(userId));
    }
}
