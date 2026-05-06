package com.hms.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        boolean success,
        String errorCode,
        String message,
        LocalDateTime timestamp,
        Map<String, String> fieldErrors
) {
    public static ErrorResponse of(String errorCode, String message) {
        return new ErrorResponse(false, errorCode, message, LocalDateTime.now(), Map.of());
    }

    public static ErrorResponse validation(Map<String, String> fieldErrors) {
        return new ErrorResponse(false, "VALIDATION_FAILED", "Request validation failed", LocalDateTime.now(), fieldErrors);
    }
}
