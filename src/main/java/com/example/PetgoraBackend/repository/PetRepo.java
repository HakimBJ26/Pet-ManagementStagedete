package com.example.PetgoraBackend.repository;

import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.entity.SafeZone;
import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PetRepo extends JpaRepository<Pet, Integer> {
    List<Pet> findByOwner_Id(Integer ownerId);



}
