package com.hms.dto.response;

import com.hms.enums.AdmissionStatus;

import java.time.LocalDateTime;

public record AdmissionResponse(
        Long id,
        Long patientId,
        String patientName,
        Long doctorId,
        String doctorName,
        AdmissionStatus status,
        String ward,
        String bedNumber,
        LocalDateTime admittedAt,
        LocalDateTime dischargedAt,
        String diagnosis,
        String dischargeSummary
) {
}
