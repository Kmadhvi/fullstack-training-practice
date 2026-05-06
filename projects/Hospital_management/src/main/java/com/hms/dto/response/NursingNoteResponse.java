package com.hms.dto.response;

import java.time.LocalDateTime;

public record NursingNoteResponse(
        Long id,
        Long admissionId,
        Long patientId,
        String patientName,
        Long nurseId,
        String nurseName,
        String note,
        LocalDateTime recordedAt
) {
}
