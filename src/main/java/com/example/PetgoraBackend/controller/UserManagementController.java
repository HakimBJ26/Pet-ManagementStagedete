package com.example.PetgoraBackend.controller;

import com.example.PetgoraBackend.entity.UserDto;
import com.example.PetgoraBackend.entity.UserLoginDto;
import com.example.PetgoraBackend.service.IUsersManagementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



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




}
