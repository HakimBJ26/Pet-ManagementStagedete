package com.example.PetgoraBackend.repository;

import com.example.PetgoraBackend.entities.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<OurUsers, Integer> {
        Optional<OurUsers> findByEmail(String email);

}
