package com.example.PetgoraBackend.service.implementations;

import com.example.PetgoraBackend.dto.UserRegistrationModel;
import com.example.PetgoraBackend.entity.User;
import com.example.PetgoraBackend.entity.UserDto;
import com.example.PetgoraBackend.exception.RessourceNotFoundException;
import com.example.PetgoraBackend.mapper.UserMapper;
import com.example.PetgoraBackend.repository.UsersRepo;
import com.example.PetgoraBackend.service.IUsersManagementService;
import com.example.PetgoraBackend.service.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
            throw new IllegalArgumentException("User with Email: "+userDto.email()+"already exist");
        } else {
            User newUser = new User();
            newUser.setName(userDto.name());
            newUser.setEmail(userDto.email());
            newUser.setCity(userDto.city());
            newUser.setRole(userDto.role());
            return UserMapper.INSTANCE.toUserDto(usersRepo.save(newUser));
          }
        }

}

