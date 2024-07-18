package com.example.PetgoraBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PetgoraBackend.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface  UsersRepo extends JpaRepository<User, Integer> {

        Optional<User> findUserByEmail(String email);
        boolean existsUserByEmail(String email);

        Optional<User> findUserById(Long userId);

        void deleteById(Long userId);
}
