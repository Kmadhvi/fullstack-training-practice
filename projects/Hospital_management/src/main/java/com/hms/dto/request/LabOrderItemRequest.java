package com.hms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LabOrderItemRequest(
        @NotBlank @Size(max = 180) String testName,
        @NotBlank @Size(max = 80) String sampleType
) {
}
