package com.hms.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record DoctorRequest(
        @NotNull(message = "User id is required")
        Long userId,

        @NotNull(message = "Department id is required")
        Long departmentId,

        @NotBlank(message = "License number is required")
        @Size(max = 80, message = "License number must be at most 80 characters")
        String licenseNumber,

        @NotBlank(message = "Specialization is required")
        @Size(max = 120, message = "Specialization must be at most 120 characters")
        String specialization,

        @NotBlank(message = "Qualification is required")
        @Size(max = 160, message = "Qualification must be at most 160 characters")
        String qualification,

        @NotNull(message = "Consultation fee is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "Consultation fee cannot be negative")
        BigDecimal consultationFee,

        boolean available
) {
}
