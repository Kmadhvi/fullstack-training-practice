package com.hms.dto.request;

import com.hms.enums.AppointmentType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record AppointmentRequest(
        @NotNull(message = "Patient id is required")
        Long patientId,

        @NotNull(message = "Doctor id is required")
        Long doctorId,

        @NotNull(message = "Appointment type is required")
        AppointmentType appointmentType,

        @NotNull(message = "Appointment time is required")
        @Future(message = "Appointment time must be in the future")
        LocalDateTime appointmentAt,

        @Size(max = 500, message = "Reason must be at most 500 characters")
        String reason,

        boolean walkIn
) {
}
