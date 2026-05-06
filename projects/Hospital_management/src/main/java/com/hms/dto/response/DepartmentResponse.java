package com.hms.dto.response;

public record DepartmentResponse(
        Long id,
        String name,
        String code,
        String description,
        boolean active
) {
}
