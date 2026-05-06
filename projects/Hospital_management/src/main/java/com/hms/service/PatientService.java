package com.hms.service;

import com.hms.dto.request.PatientRequest;
import com.hms.dto.response.PatientResponse;

import java.util.List;

public interface PatientService {
    PatientResponse create(PatientRequest request);

    PatientResponse update(Long id, PatientRequest request);

    PatientResponse getById(Long id);

    List<PatientResponse> search(String query);

    List<PatientResponse> getAll();
}
