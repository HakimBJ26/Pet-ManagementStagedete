package com.example.PetgoraBackend.repository;

import com.example.PetgoraBackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<User, Long> {
        Optional<User> findByEmail(String email);

}
