package com.hms.dto.response;

import com.hms.enums.BillItemType;

import java.math.BigDecimal;

public record BillItemResponse(
        Long id,
        BillItemType itemType,
        String description,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal amount
) {
}
