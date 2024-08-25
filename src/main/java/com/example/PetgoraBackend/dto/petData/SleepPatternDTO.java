package com.example.PetgoraBackend.dto.petData;
import lombok.Data;
import java.time.LocalDate;

@Data
public class SleepPatternDTO {
    private Long id;
    private Long petId;
    private String duration;
    private LocalDate day;
}
