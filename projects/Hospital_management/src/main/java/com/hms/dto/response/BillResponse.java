package com.hms.dto.response;

import com.hms.enums.BillStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record BillResponse(
        Long id,
        Long patientId,
        String patientName,
        String billNumber,
        BillStatus status,
        BigDecimal subtotal,
        BigDecimal discount,
        BigDecimal tax,
        BigDecimal totalAmount,
        BigDecimal paidAmount,
        BigDecimal outstandingAmount,
        LocalDateTime issuedAt,
        List<BillItemResponse> items
) {
}
