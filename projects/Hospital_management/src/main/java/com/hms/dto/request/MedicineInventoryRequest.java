package com.hms.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MedicineInventoryRequest(
        @NotBlank String medicineName,
        @NotBlank String batchNumber,
        String manufacturer,
        @NotNull @Future LocalDate expiryDate,
        @NotNull @Min(0) Integer quantityAvailable,
        @NotNull @Min(0) Integer reorderLevel,
        @NotNull @DecimalMin("0.0") BigDecimal unitPrice
) {
}
