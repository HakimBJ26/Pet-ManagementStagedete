package com.example.PetgoraBackend.service;


import com.example.PetgoraBackend.dto.ReqRes;
import com.example.PetgoraBackend.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OurUserDetailsService implements UserDetailsService {


    @Autowired
    private UsersRepo usersRepo;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return usersRepo.findByEmail(username).orElseThrow();

        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return usersRepo.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        }






    }
