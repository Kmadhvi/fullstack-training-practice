package com.hms.service;

import com.hms.dto.request.VitalRequest;
import com.hms.dto.response.VitalResponse;

import java.util.List;

public interface VitalService {
    VitalResponse record(VitalRequest request);

    List<VitalResponse> getByPatient(Long patientId);

    List<VitalResponse> getByAdmission(Long admissionId);
}
