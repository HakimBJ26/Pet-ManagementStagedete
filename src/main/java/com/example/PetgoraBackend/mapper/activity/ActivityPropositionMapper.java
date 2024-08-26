package com.example.PetgoraBackend.mapper.activity;


import com.example.PetgoraBackend.dto.activity.ActivityPropositionDTO;
import com.example.PetgoraBackend.entity.acitvity.ActivityProposition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ActivityPropositionMapper {

    ActivityProposition toEntity(ActivityPropositionDTO activityPropositionDTO);


    ActivityPropositionDTO toDto(ActivityProposition activityProposition);
}


