package com.hms.dto.request;

import com.hms.enums.LabOrderStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LabResultRequest(
        @NotNull LabOrderStatus status,
        @Size(max = 255) String resultValue,
        @Size(max = 120) String referenceRange,
        @Size(max = 500) String remarks
) {
}
