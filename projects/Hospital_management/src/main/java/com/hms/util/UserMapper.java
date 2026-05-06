package com.hms.util;

import com.hms.dto.response.UserResponse;
import com.hms.entity.Department;
import com.hms.entity.User;

public final class UserMapper {

    private UserMapper() {
    }

    public static UserResponse toResponse(User user) {
        Department department = user.getDepartment();
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                department == null ? null : department.getId(),
                department == null ? null : department.getName(),
                user.getPhone(),
                user.isEnabled(),
                user.isAccountNonLocked(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
