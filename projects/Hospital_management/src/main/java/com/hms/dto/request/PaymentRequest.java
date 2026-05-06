package com.hms.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentRequest(
        @NotNull @DecimalMin(value = "0.01", message = "Payment amount must be greater than zero") BigDecimal amount
) {
}
