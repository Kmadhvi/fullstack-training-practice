package com.hms.service;

import com.hms.dto.request.AdmissionRequest;
import com.hms.dto.request.DischargeRequest;
import com.hms.dto.response.AdmissionResponse;

import java.util.List;

public interface AdmissionService {
    AdmissionResponse admit(AdmissionRequest request);

    AdmissionResponse discharge(Long id, DischargeRequest request);

    AdmissionResponse getById(Long id);

    List<AdmissionResponse> getByPatient(Long patientId);
}
