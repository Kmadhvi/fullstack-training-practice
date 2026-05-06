package com.hms.dto.response;

import com.hms.enums.UserRole;

public record AuthResponse(
        String token,
        String tokenType,
        Long userId,
        String fullName,
        String email,
        UserRole role,
        Long departmentId
) {
}
