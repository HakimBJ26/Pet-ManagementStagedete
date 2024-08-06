package com.example.PetgoraBackend.mapper;

import com.example.PetgoraBackend.dto.VaccineRecordDto;
import com.example.PetgoraBackend.entity.VaccineRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VaccineRecordMapper {
    VaccineRecordMapper INSTANCE = Mappers.getMapper(VaccineRecordMapper.class);

    VaccineRecordDto toVaccineRecordDto(VaccineRecord vaccineRecord);
    VaccineRecord toVaccineRecord(VaccineRecordDto vaccineRecordDto);
    void updateVaccineRecordFromDto(VaccineRecordDto vaccineRecordDto, @MappingTarget VaccineRecord vaccineRecord);
}
