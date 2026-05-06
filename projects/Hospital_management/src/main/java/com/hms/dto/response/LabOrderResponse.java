package com.hms.dto.response;

import com.hms.enums.LabOrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record LabOrderResponse(
        Long id,
        Long patientId,
        String patientName,
        Long doctorId,
        String doctorName,
        Long consultationId,
        LabOrderStatus status,
        LocalDateTime orderedAt,
        LocalDateTime completedAt,
        List<LabOrderItemResponse> items
) {
}
