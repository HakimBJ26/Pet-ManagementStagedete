package com.example.PetgoraBackend.mapper;

import com.example.PetgoraBackend.dto.HealthReminderDto;
import com.example.PetgoraBackend.dto.VaccineRecordDto;
import com.example.PetgoraBackend.entity.HealthReminder;
import com.example.PetgoraBackend.entity.VaccineRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HealthReminderMapper {
    HealthReminderMapper INSTANCE = Mappers.getMapper(HealthReminderMapper.class);

    HealthReminderDto toHealthReminderDto(HealthReminder healthReminder);
    HealthReminder toHealthReminder(HealthReminderDto healthReminderDto);
    void updateHealthReminderFromDto(HealthReminderDto HealthReminderDto, @MappingTarget HealthReminder healthReminder);

}
