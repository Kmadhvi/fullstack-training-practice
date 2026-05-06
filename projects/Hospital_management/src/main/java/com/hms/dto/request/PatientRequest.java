package com.hms.dto.request;

import com.hms.enums.BloodGroup;
import com.hms.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PatientRequest(
        Long userId,

        @NotBlank(message = "MRN is required")
        @Size(max = 40, message = "MRN must be at most 40 characters")
        String mrn,

        @NotBlank(message = "First name is required")
        @Size(max = 80, message = "First name must be at most 80 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 80, message = "Last name must be at most 80 characters")
        String lastName,

        @NotNull(message = "Gender is required")
        Gender gender,

        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth,

        BloodGroup bloodGroup,

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^[0-9+\\-() ]{7,30}$", message = "Phone number is invalid")
        String phone,

        @Email(message = "Email must be valid")
        @Size(max = 180, message = "Email must be at most 180 characters")
        String email,

        @Size(max = 500, message = "Address must be at most 500 characters")
        String address,

        @Size(max = 120, message = "Emergency contact name must be at most 120 characters")
        String emergencyContactName,

        @Pattern(regexp = "^$|^[0-9+\\-() ]{7,30}$", message = "Emergency phone number is invalid")
        String emergencyContactPhone,

        String allergies,

        String medicalHistory
) {
}
