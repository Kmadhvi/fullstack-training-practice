package com.hms.service;

import com.hms.dto.request.DoctorRequest;
import com.hms.dto.response.DoctorResponse;

import java.util.List;

public interface DoctorService {
    DoctorResponse create(DoctorRequest request);

    DoctorResponse update(Long id, DoctorRequest request);

    DoctorResponse getById(Long id);

    List<DoctorResponse> getAll();

    List<DoctorResponse> getAvailableByDepartment(Long departmentId);
}
