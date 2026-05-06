package com.hms.dto.response;

import java.time.LocalDateTime;

public record AuditLogResponse(
        Long id,
        Long userId,
        String userEmail,
        String action,
        String entityName,
        Long entityId,
        String details,
        String ipAddress,
        LocalDateTime createdAt
) {
}
