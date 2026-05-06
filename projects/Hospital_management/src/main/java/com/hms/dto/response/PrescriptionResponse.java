package com.hms.dto.response;

import com.hms.enums.PrescriptionStatus;

import java.time.LocalDateTime;
import java.util.List;

public record PrescriptionResponse(
        Long id,
        Long consultationId,
        Long patientId,
        String patientName,
        Long doctorId,
        String doctorName,
        PrescriptionStatus status,
        String instructions,
        LocalDateTime issuedAt,
        List<PrescriptionItemResponse> items
) {
}
