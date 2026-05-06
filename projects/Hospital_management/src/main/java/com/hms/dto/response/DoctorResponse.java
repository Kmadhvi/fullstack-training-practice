package com.hms.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DoctorResponse(
        Long id,
        Long userId,
        String fullName,
        String email,
        Long departmentId,
        String departmentName,
        String licenseNumber,
        String specialization,
        String qualification,
        BigDecimal consultationFee,
        boolean available,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
