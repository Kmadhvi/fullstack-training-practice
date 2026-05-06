package com.hms.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record LabOrderRequest(
        @NotNull Long patientId,
        @NotNull Long doctorId,
        Long consultationId,
        @Valid @NotEmpty List<LabOrderItemRequest> items
) {
}
