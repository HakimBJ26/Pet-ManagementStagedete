package com.example.PetgoraBackend.service.implementations;
import com.example.PetgoraBackend.entity.User;
import com.example.PetgoraBackend.dto.UserDto;
import com.example.PetgoraBackend.dto.UserLoginDto;
import com.example.PetgoraBackend.mapper.UserMapper;
import com.example.PetgoraBackend.repository.UsersRepo;
import com.example.PetgoraBackend.service.IUsersManagementService;
import com.example.PetgoraBackend.config.JWTUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImp implements IUsersManagementService {

    private final UsersRepo usersRepo;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    public UserServiceImp(UsersRepo usersRepo,
                          JWTUtils jwtUtils,
                          AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.usersRepo = usersRepo;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
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
            newUser.setPhone(userDto.phone());
            newUser.setPassword(passwordEncoder.encode(userDto.password()));
            sendConfirmationEmail(userDto);
            return UserMapper.INSTANCE.toUserDto(usersRepo.save(newUser));
        }
    }


    private void sendConfirmationEmail(UserDto userDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ktarichaima6@gmail.com");
        message.setSubject("Account Creation Confirmation");
        message.setTo(userDto.email());
        message.setText("Dear " + userDto.name() + ",\n\nYour account has been successfully created.\n\nSincerely,\nPetaGora");
        mailSender.send(message);
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


            ResponseCookie accessTokenCookie = jwtUtils.generateAccessTokenCookie(userDetails);
            ResponseCookie refreshTokenCookie = jwtUtils.generateRefreshTokenCookie(userDetails);

            response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
            response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

            UserDto userDto = new UserDto(
                    user.getId(),
                    user.getName(),
                    user.getCity(),
                    user.getRole(),
                    user.getEmail(),
                    user.getPhone(),
                    null
            );

            return ResponseEntity.ok(userDto);
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Incorrect email or password", e);
        }
    }

    @Override
    public UserDto updateUserProfile(UserDto userDto, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();

        Optional<User> currentUserOptional = usersRepo.findUserByEmail(currentEmail);
        if (currentUserOptional.isPresent()) {
            User currentUser = currentUserOptional.get();

            currentUser.setName(userDto.name());
            currentUser.setCity(userDto.city());
            currentUser.setPhone(userDto.phone());
            User updatedUser = usersRepo.save(currentUser);

            // Generate new JWT token
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(updatedUser.getEmail(), "", new ArrayList<>());

            // Set the new access and refresh tokens in the HttpOnly cookies
            ResponseCookie accessTokenCookie = jwtUtils.generateAccessTokenCookie(userDetails);
            ResponseCookie refreshTokenCookie = jwtUtils.generateRefreshTokenCookie(userDetails);

            response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
            response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

            return UserMapper.INSTANCE.toUserDto(updatedUser);
        } else {
            throw new EntityNotFoundException("User with email: " + currentEmail + " not found");
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateUserByAdmin(int userId, UserDto userDto) { // Changed Long to int
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();

        User currentUser = usersRepo.findUserByEmail(currentEmail)
                .orElseThrow(() -> new EntityNotFoundException("Current user not found"));

        User userToUpdate = usersRepo.findUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID: " + userId + " not found"));

        if (!userToUpdate.getRole().equalsIgnoreCase("ADMIN")) {
            if (currentUser.getRole().equalsIgnoreCase("ADMIN")) {
                userToUpdate.setName(userDto.name());
                userToUpdate.setCity(userDto.city());
                userToUpdate.setPhone(userDto.phone());
                User updatedUser = usersRepo.save(userToUpdate);

                return ResponseEntity.ok("User updated successfully.");
            } else {
                throw new AccessDeniedException("Administrators are not allowed to update other administrators.");
            }
        } else {
            throw new AccessDeniedException("Administrators cannot update other administrators.");
        }
    }

    @Override
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    String refreshToken = cookie.getValue();
                    if (jwtUtils.isTokenValid(refreshToken)) {
                        String username = jwtUtils.extractUsername(refreshToken);
                        UserDetails userDetails = usersRepo.findUserByEmail(username).orElseThrow(() -> new EntityNotFoundException("User not found"));

                        // Generate new access token
                        ResponseCookie newAccessTokenCookie = jwtUtils.generateAccessTokenCookie(userDetails);
                        response.addHeader(HttpHeaders.SET_COOKIE, newAccessTokenCookie.toString());

                        return ResponseEntity.ok("Token refreshed successfully");
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
                    }
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is missing");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<String> deleteUserById(int userId) {
        try {
            usersRepo.deleteById(userId);
            return ResponseEntity.ok("User deleted with success");
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: You do not have permission to delete users.");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = usersRepo.findAll();
        return users.stream()
                .map(UserMapper.INSTANCE::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(int userId) {
        Optional<User> userOptional = usersRepo.findUserById(userId);
        User user = userOptional.orElseThrow(() -> new EntityNotFoundException("User with ID: " + userId + " not found"));

        return UserMapper.INSTANCE.toUserDto(user);
    }

    @Override
    public UserDto getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Optional<User> userOptional = usersRepo.findUserByEmail(userEmail);
        User user = userOptional.orElseThrow(() -> new EntityNotFoundException("User with email: " + userEmail + " not found"));

        return UserMapper.INSTANCE.toUserDto(user);
    }

    public void logout(HttpServletResponse response) {
        ResponseCookie cleanAccessTokenCookie = jwtUtils.getCleanAccessTokenCookie();
        ResponseCookie cleanRefreshTokenCookie = jwtUtils.getCleanRefreshTokenCookie();

        response.addHeader(HttpHeaders.SET_COOKIE, cleanAccessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, cleanRefreshTokenCookie.toString());
    }
}
