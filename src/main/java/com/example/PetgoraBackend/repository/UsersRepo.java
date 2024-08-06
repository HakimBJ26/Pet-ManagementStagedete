package com.example.PetgoraBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PetgoraBackend.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface  UsersRepo extends JpaRepository<User, Integer> {

        Optional<User> findUserByEmail(String email);

        Optional<User> findUserById(int userId);

        void deleteById(int userId);

        List<User> findByApprovedFalse();

        Optional<User> findUserByResetPasswordToken(String token);

        @Query("SELECT u FROM User u WHERE u.role = 'VETERINARIAN' AND LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
        List<User> findVeterinariansByName(String name);

}

