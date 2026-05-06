package com.hms.dto.request;

import com.hms.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(
        @NotBlank(message = "Full name is required")
        @Size(max = 160, message = "Full name must be at most 160 characters")
        String fullName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Size(max = 180, message = "Email must be at most 180 characters")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 72, message = "Password must be between 8 and 72 characters")
        String password,

        @NotNull(message = "Role is required")
        UserRole role,

        Long departmentId,

        @Pattern(regexp = "^[0-9+\\-() ]{7,30}$", message = "Phone number is invalid")
        String phone
) {
}
