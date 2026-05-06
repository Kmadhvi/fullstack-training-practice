package com.hms.util;

import com.hms.dto.response.PatientResponse;
import com.hms.entity.Patient;

import java.time.LocalDate;
import java.time.Period;

public final class PatientMapper {

    private PatientMapper() {
    }

    public static PatientResponse toResponse(Patient patient) {
        String fullName = patient.getFirstName() + " " + patient.getLastName();
        Integer age = patient.getDateOfBirth() == null ? null : Period.between(patient.getDateOfBirth(), LocalDate.now()).getYears();
        return new PatientResponse(
                patient.getId(),
                patient.getUser() == null ? null : patient.getUser().getId(),
                patient.getMrn(),
                patient.getFirstName(),
                patient.getLastName(),
                fullName,
                patient.getGender(),
                patient.getDateOfBirth(),
                age,
                patient.getBloodGroup(),
                patient.getPhone(),
                patient.getEmail(),
                patient.getAddress(),
                patient.getEmergencyContactName(),
                patient.getEmergencyContactPhone(),
                patient.getAllergies(),
                patient.getMedicalHistory(),
                patient.getCreatedAt(),
                patient.getUpdatedAt()
        );
    }
}
