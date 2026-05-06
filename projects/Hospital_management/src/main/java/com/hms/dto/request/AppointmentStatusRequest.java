package com.hms.dto.request;

import com.hms.enums.AppointmentStatus;
import jakarta.validation.constraints.NotNull;

public record AppointmentStatusRequest(
        @NotNull(message = "Status is required")
        AppointmentStatus status
) {
}
