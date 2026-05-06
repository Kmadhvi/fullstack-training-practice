package com.hms.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VitalRequest(
        @NotNull Long patientId,
        @NotNull Long recordedBy,
        Long admissionId,
        BigDecimal temperatureCelsius,
        @Min(20) @Max(250) Integer pulseRate,
        @Min(5) @Max(80) Integer respiratoryRate,
        @Min(50) @Max(260) Integer systolicBp,
        @Min(30) @Max(180) Integer diastolicBp,
        @Min(40) @Max(100) Integer oxygenSaturation,
        BigDecimal weightKg,
        @Size(max = 500) String notes,
        LocalDateTime recordedAt
) {
}
