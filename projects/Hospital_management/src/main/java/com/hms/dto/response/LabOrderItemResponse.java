package com.hms.dto.response;

import com.hms.enums.LabOrderStatus;

public record LabOrderItemResponse(
        Long id,
        String testName,
        String sampleType,
        LabOrderStatus status,
        String resultValue,
        String referenceRange,
        String remarks
) {
}
