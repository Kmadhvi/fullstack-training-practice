package com.hms.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VitalResponse(
        Long id,
        Long patientId,
        String patientName,
        Long recordedBy,
        String recordedByName,
        Long admissionId,
        BigDecimal temperatureCelsius,
        Integer pulseRate,
        Integer respiratoryRate,
        Integer systolicBp,
        Integer diastolicBp,
        Integer oxygenSaturation,
        BigDecimal weightKg,
        String notes,
        LocalDateTime recordedAt
) {
}
