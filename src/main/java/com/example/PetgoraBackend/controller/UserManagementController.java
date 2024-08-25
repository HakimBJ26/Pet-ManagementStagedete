package com.example.PetgoraBackend.controller;

import com.example.PetgoraBackend.dto.UserDto;
import com.example.PetgoraBackend.dto.UserLoginDto;
import com.example.PetgoraBackend.service.IUsersManagementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PutMapping("/updateProfile")
    public ResponseEntity<UserDto> updateUserProfile(@RequestBody UserDto userDto, HttpServletResponse response) {
        UserDto updatedUserDto = usersManagementService.updateUserProfile(userDto, response);
        return ResponseEntity.ok(updatedUserDto);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return usersManagementService.refreshToken(request, response);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        return usersManagementService.deleteUserById(userId);
    }

    @GetMapping("/GetAllUsers")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = usersManagementService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/updateUserByAdmin/{userId}")
    public ResponseEntity<String> updateUserByAdmin(@PathVariable int userId, @RequestBody UserDto userDto) { // Changed Long to int
        return usersManagementService.updateUserByAdmin(userId, userDto);
    }

    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int userId) {
        return ResponseEntity.ok(usersManagementService.getUserById(userId));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        usersManagementService.logout(response);
        return ResponseEntity.ok("Logged out successfully.");
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile() {
        UserDto userProfile = usersManagementService.getUserProfile();
        return ResponseEntity.ok(userProfile);
    }

    @PutMapping("/approveUserByEmail")
    public ResponseEntity<String> approveUserByEmail(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        usersManagementService.approveUserByEmail(email);
        return ResponseEntity.ok("User approved successfully.");
    }

    @GetMapping("/unapprovedUsers")
    public ResponseEntity<List<UserDto>> getUnapprovedUsers() {
        List<UserDto> unapprovedUsers = usersManagementService.getUnapprovedUsers();
        return ResponseEntity.ok(unapprovedUsers);
    }


    @PostMapping("/sendResetPasswordEmail")
    public ResponseEntity<String> sendResetPasswordEmail(@RequestBody Map<String, String> requestBody) {
        return usersManagementService.sendResetPasswordEmail(requestBody.get("email"));
    }

    @PostMapping("/verifyResetPasswordToken")
    public ResponseEntity<String> verifyResetPasswordToken(@RequestBody Map<String, String> requestBody, HttpServletResponse response) {
        return usersManagementService.verifyResetPasswordToken(requestBody.get("token"), response);
    }

    @PutMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> requestBody, @CookieValue(value = "resetPasswordToken", required = false) String token,  HttpServletResponse response) {
        return usersManagementService.resetPassword(requestBody.get("newPassword"), requestBody.get("confirmPassword"), token, response);
    }
    @GetMapping("/search-veterinarians")
    public List<UserDto> searchVeterinarians(@RequestParam String name) {
        return usersManagementService.searchVeterinariansByName(name);
    }

    @PostMapping("/messaging-token/{id}")
    public ResponseEntity<Void> saveMessagingToken(@PathVariable Integer id, @RequestBody String token) {
        usersManagementService.saveMessagingToken(id, token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/messaging-token/{id}")
    public ResponseEntity<String> getMessagingToken(@PathVariable Integer id) {
        String token = usersManagementService.getMessagingTokenById(id);
        return ResponseEntity.ok(token);
    }

    @DeleteMapping("/messaging-token/{id}")
    public ResponseEntity<Void> removeMessagingToken(@PathVariable Integer id) {
        usersManagementService.removeMessagingToken(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/image-url/{userId}")
    public void updateUserImageUrl(@PathVariable Integer userId, @RequestParam String userImageUrl) {
        usersManagementService.updateUserImageUrl(userId, userImageUrl);
    }

    @GetMapping("/image-url/{userId}")
    public ResponseEntity<String> getUserImageUrl(@PathVariable Integer userId) {
        String userImageUrl = usersManagementService.getUserImageUrl(userId);
        return ResponseEntity.ok(userImageUrl);
    }

}
