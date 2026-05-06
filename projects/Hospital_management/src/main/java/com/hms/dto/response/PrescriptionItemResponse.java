package com.hms.dto.response;

public record PrescriptionItemResponse(
        Long id,
        String medicineName,
        String dosage,
        String frequency,
        Integer durationDays,
        Integer quantity,
        String instructions
) {
}
