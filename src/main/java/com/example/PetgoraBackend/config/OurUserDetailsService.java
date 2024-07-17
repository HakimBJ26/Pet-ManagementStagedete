package com.example.PetgoraBackend.config;


import com.example.PetgoraBackend.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OurUserDetailsService implements UserDetailsService {


    @Autowired
    private UsersRepo usersRepo;

        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return usersRepo.findUserByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        }






    }
