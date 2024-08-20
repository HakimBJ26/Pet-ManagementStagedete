package com.example.PetgoraBackend.mapper;

import com.example.PetgoraBackend.dto.MedicalRecordDto;
import com.example.PetgoraBackend.dto.VaccineRecordDto;
import com.example.PetgoraBackend.entity.MedicalRecord;
import com.example.PetgoraBackend.entity.VaccineRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MedicalRecordMapper {
    MedicalRecordMapper INSTANCE = Mappers.getMapper(MedicalRecordMapper.class);

    MedicalRecordDto toMedicalRecordDto(MedicalRecord medicalRecord);
    MedicalRecord toMedicalRecord(MedicalRecordDto medicalRecordDto);
    void updateMedicalRecordFromDto(MedicalRecordDto medicalRecordDto, @MappingTarget MedicalRecord medicalRecord);

}
