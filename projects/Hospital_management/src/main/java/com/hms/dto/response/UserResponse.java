package com.hms.dto.response;

import com.hms.enums.UserRole;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        UserRole role,
        Long departmentId,
        String departmentName,
        String phone,
        boolean enabled,
        boolean accountNonLocked,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
