package com.hms.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MedicineInventoryResponse(
        Long id,
        String medicineName,
        String batchNumber,
        String manufacturer,
        LocalDate expiryDate,
        Integer quantityAvailable,
        Integer reorderLevel,
        BigDecimal unitPrice,
        boolean lowStock
) {
}
