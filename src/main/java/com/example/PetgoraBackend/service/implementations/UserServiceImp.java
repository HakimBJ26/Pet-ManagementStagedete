package com.example.PetgoraBackend.service.implementations;


import com.example.PetgoraBackend.entity.User;
import com.example.PetgoraBackend.entity.UserDto;

import com.example.PetgoraBackend.entity.UserLoginDto;
import com.example.PetgoraBackend.mapper.UserMapper;
import com.example.PetgoraBackend.repository.UsersRepo;
import com.example.PetgoraBackend.service.IUsersManagementService;
import com.example.PetgoraBackend.config.JWTUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImp implements IUsersManagementService {

    private final UsersRepo usersRepo;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImp(UsersRepo usersRepo,
                          JWTUtils jwtUtils,
                          AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder) {
        this.usersRepo = usersRepo;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        Optional<User> user = this.usersRepo.findUserByEmail(userDto.email());

        if (user.isPresent()) {
            throw new IllegalArgumentException("User with Email: " + userDto.email() + " already exists");
        } else {
            User newUser = new User();
            newUser.setName(userDto.name());
            newUser.setEmail(userDto.email());
            newUser.setCity(userDto.city());
            newUser.setRole(userDto.role());
            newUser.setPassword(passwordEncoder.encode(userDto.password()));
            return UserMapper.INSTANCE.toUserDto(usersRepo.save(newUser));
        }
    }

    @Override
    public ResponseEntity<UserDto> UserLogin(UserLoginDto userLoginDto, HttpServletResponse response) {
        try {

            User user = usersRepo.findUserByEmail(userLoginDto.email())
                    .orElseThrow(() -> new IllegalArgumentException("User does not exist"));

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userLoginDto.email(), userLoginDto.password());
            authenticationManager.authenticate(authenticationToken);

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    user.getEmail(), user.getPassword(), Collections.emptyList());
            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
            response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

            return ResponseEntity.ok().build();

        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Incorrect email or password", e);
        }
    }

    @Override
    public UserDto updateUserProfile(UserDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();

        Optional<User> currentUserOptional = usersRepo.findUserByEmail(currentEmail);
        if (currentUserOptional.isPresent()) {
            User currentUser = currentUserOptional.get();

            if (!currentEmail.equals(userDto.email())) {
                Optional<User> newUserWithEmailOptional = usersRepo.findUserByEmail(userDto.email());
                if (newUserWithEmailOptional.isPresent()) {
                    throw new IllegalArgumentException("Email already in use: " + userDto.email());
                }
            }
            currentUser.setName(userDto.name());
            currentUser.setEmail(userDto.email());

            User updatedUser = usersRepo.save(currentUser);

            return UserMapper.INSTANCE.toUserDto(updatedUser);
        } else {

            throw new EntityNotFoundException("User with email: " + currentEmail + " not found");
        }
    }


    @Override
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    String refreshToken = cookie.getValue();
                    if (jwtUtils.isTokenExpired(refreshToken)) {
                        return ResponseEntity.status(401).body("Refresh token is expired");
                    }
                    String username = jwtUtils.extractUsername(refreshToken);
                    UserDetails userDetails = usersRepo.findUserByEmail(username)
                            .map(user -> new org.springframework.security.core.userdetails.User(
                                    user.getEmail(), user.getPassword(), Collections.emptyList()))
                            .orElseThrow(() -> new IllegalArgumentException("User does not exist"));

                    if (jwtUtils.isTokenValid(refreshToken, userDetails)) {
                        ResponseCookie newJwtCookie = jwtUtils.generateJwtCookie(userDetails);
                        response.addHeader(HttpHeaders.SET_COOKIE, newJwtCookie.toString());
                        return ResponseEntity.ok("Token refreshed successfully");
                    } else {
                        return ResponseEntity.status(401).body("Invalid refresh token");
                    }
                }
            }
        }
        return ResponseEntity.status(400).body("Refresh token not found");
    }
}






