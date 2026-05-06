package com.hms.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record BillRequest(
        @NotNull Long patientId,
        @DecimalMin("0.0") BigDecimal discount,
        @DecimalMin("0.0") BigDecimal tax,
        @Valid @NotEmpty List<BillItemRequest> items
) {
}
