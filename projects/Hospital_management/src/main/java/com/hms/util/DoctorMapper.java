package com.hms.util;

import com.hms.dto.response.DoctorResponse;
import com.hms.entity.Department;
import com.hms.entity.Doctor;
import com.hms.entity.User;

public final class DoctorMapper {

    private DoctorMapper() {
    }

    public static DoctorResponse toResponse(Doctor doctor) {
        User user = doctor.getUser();
        Department department = doctor.getDepartment();
        return new DoctorResponse(
                doctor.getId(),
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                department.getId(),
                department.getName(),
                doctor.getLicenseNumber(),
                doctor.getSpecialization(),
                doctor.getQualification(),
                doctor.getConsultationFee(),
                doctor.isAvailable(),
                doctor.getCreatedAt(),
                doctor.getUpdatedAt()
        );
    }
}
