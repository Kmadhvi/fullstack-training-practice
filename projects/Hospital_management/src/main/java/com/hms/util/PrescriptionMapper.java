package com.hms.util;

import com.hms.dto.response.PrescriptionItemResponse;
import com.hms.dto.response.PrescriptionResponse;
import com.hms.entity.Prescription;
import com.hms.entity.PrescriptionItem;

public final class PrescriptionMapper {

    private PrescriptionMapper() {
    }

    public static PrescriptionResponse toResponse(Prescription prescription) {
        return new PrescriptionResponse(
                prescription.getId(),
                prescription.getConsultation().getId(),
                prescription.getPatient().getId(),
                prescription.getPatient().getFirstName() + " " + prescription.getPatient().getLastName(),
                prescription.getDoctor().getId(),
                prescription.getDoctor().getUser().getFullName(),
                prescription.getStatus(),
                prescription.getInstructions(),
                prescription.getIssuedAt(),
                prescription.getItems().stream().map(PrescriptionMapper::toItemResponse).toList()
        );
    }

    private static PrescriptionItemResponse toItemResponse(PrescriptionItem item) {
        return new PrescriptionItemResponse(item.getId(), item.getMedicineName(), item.getDosage(), item.getFrequency(), item.getDurationDays(), item.getQuantity(), item.getInstructions());
    }
}
