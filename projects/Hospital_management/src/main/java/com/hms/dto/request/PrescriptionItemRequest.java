package com.hms.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PrescriptionItemRequest(
        @NotBlank String medicineName,
        @NotBlank @Size(max = 80) String dosage,
        @NotBlank @Size(max = 80) String frequency,
        @NotNull @Min(1) Integer durationDays,
        @NotNull @Min(1) Integer quantity,
        @Size(max = 300) String instructions
) {
}
