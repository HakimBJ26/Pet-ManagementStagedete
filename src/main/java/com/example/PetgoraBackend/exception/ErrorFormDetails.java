package com.example.PetgoraBackend.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorFormDetails(LocalDateTime timestamp, List<String> errors, String details) {
}
