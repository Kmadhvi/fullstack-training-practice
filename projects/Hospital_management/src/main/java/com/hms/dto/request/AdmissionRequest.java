package com.hms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record AdmissionRequest(
        @NotNull Long patientId,
        @NotNull Long doctorId,
        @NotBlank @Size(max = 80) String ward,
        @NotBlank @Size(max = 40) String bedNumber,
        LocalDateTime admittedAt,
        @Size(max = 500) String diagnosis
) {
}
