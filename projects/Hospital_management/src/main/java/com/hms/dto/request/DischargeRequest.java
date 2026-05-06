package com.hms.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record DischargeRequest(
        LocalDateTime dischargedAt,
        @NotBlank String dischargeSummary
) {
}
