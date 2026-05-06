package com.hms.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ConsultationRequest(
        Long appointmentId,

        @NotNull(message = "Patient id is required")
        Long patientId,

        @NotNull(message = "Doctor id is required")
        Long doctorId,

        @Size(max = 4000, message = "Symptoms must be at most 4000 characters")
        String symptoms,

        @Size(max = 4000, message = "Diagnosis must be at most 4000 characters")
        String diagnosis,

        @Size(max = 4000, message = "Notes must be at most 4000 characters")
        String notes,

        LocalDate followUpDate
) {
}
