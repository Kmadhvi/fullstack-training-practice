package com.hms.service;

import com.hms.dto.request.PrescriptionRequest;
import com.hms.dto.response.PrescriptionResponse;

import java.util.List;

public interface PrescriptionService {
    PrescriptionResponse create(PrescriptionRequest request);

    PrescriptionResponse getById(Long id);

    List<PrescriptionResponse> getByPatient(Long patientId);
}
