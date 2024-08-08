package com.example.PetgoraBackend.mapper;

import com.example.PetgoraBackend.dto.SurgeryRecordDto;
import com.example.PetgoraBackend.dto.VaccineRecordDto;
import com.example.PetgoraBackend.entity.SurgeryRecord;
import com.example.PetgoraBackend.entity.VaccineRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SurgeryRecordMapper {
    SurgeryRecordMapper INSTANCE = Mappers.getMapper(SurgeryRecordMapper.class);

    SurgeryRecordDto toSurgeryRecordDto(SurgeryRecord surgeryRecord);
    SurgeryRecord toSurgeryRecord(SurgeryRecordDto surgeryRecordDto);
    void updateSurgeryRecordFromDto(SurgeryRecordDto surgeryRecordDto, @MappingTarget SurgeryRecord surgeryRecord);

}
