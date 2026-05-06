package com.hms.service;

import com.hms.dto.request.DepartmentRequest;
import com.hms.dto.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
    DepartmentResponse create(DepartmentRequest request);

    DepartmentResponse update(Long id, DepartmentRequest request);

    List<DepartmentResponse> getAll();
}
