package com.hms.util;

import com.hms.dto.response.ConsultationResponse;
import com.hms.entity.Consultation;
import com.hms.entity.Patient;

public final class ConsultationMapper {

    private ConsultationMapper() {
    }

    public static ConsultationResponse toResponse(Consultation consultation) {
        Patient patient = consultation.getPatient();
        return new ConsultationResponse(
                consultation.getId(),
                consultation.getAppointment() == null ? null : consultation.getAppointment().getId(),
                patient.getId(),
                patient.getFirstName() + " " + patient.getLastName(),
                patient.getMrn(),
                consultation.getDoctor().getId(),
                consultation.getDoctor().getUser().getFullName(),
                consultation.getSymptoms(),
                consultation.getDiagnosis(),
                consultation.getNotes(),
                consultation.getFollowUpDate(),
                consultation.getCreatedAt(),
                consultation.getUpdatedAt()
        );
    }
}
