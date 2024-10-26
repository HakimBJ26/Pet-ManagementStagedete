package com.example.PetgoraBackend.dto.chat;


import java.util.Set;

public class GroupChatDTO {

    private Integer id;
    private String name;
    private String description;
    private Integer createdById;
    private Set<Integer> memberIds;

    // Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Integer createdById) {
        this.createdById = createdById;
    }

    public Set<Integer> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(Set<Integer> memberIds) {
        this.memberIds = memberIds;
    }
}
