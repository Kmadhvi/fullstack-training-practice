package com.hms.service;

import com.hms.dto.request.ConsultationRequest;
import com.hms.dto.response.ConsultationResponse;

import java.util.List;

public interface ConsultationService {
    ConsultationResponse create(ConsultationRequest request);

    ConsultationResponse update(Long id, ConsultationRequest request);

    ConsultationResponse getById(Long id);

    List<ConsultationResponse> getByPatient(Long patientId);

    List<ConsultationResponse> getByDoctor(Long doctorId);
}
