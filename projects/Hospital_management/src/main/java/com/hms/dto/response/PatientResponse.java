package com.hms.dto.response;

import com.hms.enums.BloodGroup;
import com.hms.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PatientResponse(
        Long id,
        Long userId,
        String mrn,
        String firstName,
        String lastName,
        String fullName,
        Gender gender,
        LocalDate dateOfBirth,
        Integer age,
        BloodGroup bloodGroup,
        String phone,
        String email,
        String address,
        String emergencyContactName,
        String emergencyContactPhone,
        String allergies,
        String medicalHistory,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
