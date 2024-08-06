package com.example.PetgoraBackend.mapper;

import com.example.PetgoraBackend.dto.VaccineRecordDto;
import com.example.PetgoraBackend.dto.VisitRecordDto;
import com.example.PetgoraBackend.entity.VaccineRecord;
import com.example.PetgoraBackend.entity.VisitRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VisitRecordMapper {
    VisitRecordMapper INSTANCE = Mappers.getMapper(VisitRecordMapper.class);

    VisitRecordDto toVisitRecordDto(VisitRecord visitRecord);
    VisitRecord toVisitRecord(VisitRecordDto visitRecordDto);
    void updateVisitRecordFromDto(VisitRecordDto visitRecordDto, @MappingTarget VisitRecord visitRecord);

}
