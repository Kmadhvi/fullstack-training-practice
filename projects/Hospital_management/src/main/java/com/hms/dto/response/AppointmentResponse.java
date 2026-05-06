package com.hms.dto.response;

import com.hms.enums.AppointmentStatus;
import com.hms.enums.AppointmentType;

import java.time.LocalDateTime;

public record AppointmentResponse(
        Long id,
        Long patientId,
        String patientName,
        String patientMrn,
        Long doctorId,
        String doctorName,
        String departmentName,
        AppointmentType appointmentType,
        AppointmentStatus status,
        LocalDateTime appointmentAt,
        String reason,
        Integer queueNumber,
        boolean walkIn,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
