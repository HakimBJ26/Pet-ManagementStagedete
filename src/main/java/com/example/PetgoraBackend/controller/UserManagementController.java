package com.example.PetgoraBackend.controller;

import com.example.PetgoraBackend.entity.User;
import com.example.PetgoraBackend.entity.UserDto;
import com.example.PetgoraBackend.entity.UserLoginDto;
import com.example.PetgoraBackend.service.IUsersManagementService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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





//
//    @PostMapping("/auth/refresh")
//    public ResponseEntity<String> refreshToken(@RequestBody String req){
//        return ResponseEntity.ok(usersManagementService.refreshToken(req));
//    }
//
//    @GetMapping("/admin/get-all-users")
//    public ResponseEntity<ReqRes> getAllUsers(){
//        return ResponseEntity.ok(usersManagementService.getAllUsers());
//
//    }
//
//    @GetMapping("/admin/get-users/{userId}")
//    public ResponseEntity<ReqRes> getUSerByID(@PathVariable Integer userId){
//        return ResponseEntity.ok(usersManagementService.getUsersById(userId));
//
//    }
//
//    @PutMapping("/admin/update/{userId}")
//    public ResponseEntity<ReqRes> updateUser(@PathVariable Integer userId, @RequestBody OurUsers reqres){
//        return ResponseEntity.ok(usersManagementService.updateUser(userId, reqres));
//    }
//
//    @GetMapping("/adminuser/get-profile")
//    public ResponseEntity<ReqRes> getMyProfile(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//        ReqRes response = usersManagementService.getMyInfo(email);
//        return  ResponseEntity.status(response.getStatusCode()).body(response);
//    }
//
//    @DeleteMapping("/delete/{userId}")
//    public ResponseEntity<ReqRes> deleteUSer(@PathVariable Integer userId){
//        return ResponseEntity.ok(usersManagementService.deleteUser(userId)); }
//
//
//    @PutMapping("/admin/AdminupdateUser/{userId}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public ResponseEntity<ReqRes> AdminupdateUser(@PathVariable Integer userId, @RequestBody OurUsers updatedOurUsers) {
//        ReqRes response = usersManagementService.AdminupdateUser(userId, updatedOurUsers);
//        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
//    }

}
