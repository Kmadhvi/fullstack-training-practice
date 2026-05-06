package com.hms.dto.request;

import jakarta.validation.constraints.NotNull;

public record DispenseRequest(
        @NotNull Long prescriptionId
) {
}
