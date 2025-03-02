package com.example.PetgoraBackend.entity;

import com.example.PetgoraBackend.dto.PositionPetDto;
import com.example.PetgoraBackend.entity.alerts.NotificationTimestamps;
import com.example.PetgoraBackend.entity.petData.PetGoal;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public void setPetGoal(PetGoal petGoal) {
        this.petGoal = petGoal;
    }

    public PetGoal getPetGoal() {
        return petGoal;
    }

    private String breed;

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private Integer age;

    private String imageUrl;


    private String blockchainCert;

    private boolean requestCertif;


    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    private LocalDate birthDate;  // New field for birth date

    public void setRequestCertif(boolean requestCertif) {
        this.requestCertif = requestCertif;
    }

    public boolean isRequestCertif() {
        return requestCertif;
    }

    public String getBlockchainCert() {
        return blockchainCert;
    }

    public void setBlockchainCert(String blockchainCert) {
        this.blockchainCert = blockchainCert;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @OneToOne(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private PetGoal petGoal;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id")
    private List<SafeZone> safeZones = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pet_id")
    private List<DangerZone> dangerZones = new ArrayList<>();

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToOne(mappedBy = "pet", cascade = CascadeType.ALL)
    private NotificationTimestamps notificationTimestamps;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setNotificationTimestamps(NotificationTimestamps notificationTimestamps) {
        this.notificationTimestamps = notificationTimestamps;
    }

    public List<SafeZone> getSafeZones() {
        return safeZones;
    }

    public void setSafeZones(List<SafeZone> safeZones) {
        this.safeZones = safeZones;
    }
    public List<DangerZone> getDangerZones() {
        return dangerZones;
    }

    public void setDangerZones(List<DangerZone> dangerZones) {
        this.dangerZones = dangerZones;
    }

    public NotificationTimestamps getNotificationTimestamps() {
        return notificationTimestamps;
    }
}
