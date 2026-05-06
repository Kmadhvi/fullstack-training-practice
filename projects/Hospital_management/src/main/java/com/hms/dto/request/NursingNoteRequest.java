package com.hms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record NursingNoteRequest(
        @NotNull Long admissionId,
        @NotNull Long patientId,
        @NotNull Long nurseId,
        @NotBlank String note,
        LocalDateTime recordedAt
) {
}
