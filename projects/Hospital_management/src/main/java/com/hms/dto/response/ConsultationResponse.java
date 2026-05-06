package com.hms.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ConsultationResponse(
        Long id,
        Long appointmentId,
        Long patientId,
        String patientName,
        String patientMrn,
        Long doctorId,
        String doctorName,
        String symptoms,
        String diagnosis,
        String notes,
        LocalDate followUpDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
