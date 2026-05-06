package com.hms.service.impl;

import com.hms.dto.request.DepartmentRequest;
import com.hms.dto.response.DepartmentResponse;
import com.hms.entity.Department;
import com.hms.exception.DuplicateResourceException;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.DepartmentRepository;
import com.hms.service.AuditService;
import com.hms.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final AuditService auditService;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, AuditService auditService) {
        this.departmentRepository = departmentRepository;
        this.auditService = auditService;
    }

    @Override
    @Transactional
    public DepartmentResponse create(DepartmentRequest request) {
        if (departmentRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("DEPARTMENT_EXISTS", "Department with name " + request.name() + " already exists");
        }
        Department department = new Department();
        apply(department, request);
        Department saved = departmentRepository.save(department);
        auditService.log("DEPARTMENT_CREATED", "Department", saved.getId(), "Created department");
        return map(saved);
    }

    @Override
    @Transactional
    public DepartmentResponse update(Long id, DepartmentRequest request) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("DEPARTMENT_NOT_FOUND", "Department with id " + id + " not found"));
        apply(department, request);
        auditService.log("DEPARTMENT_UPDATED", "Department", department.getId(), "Updated department");
        return map(department);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAll() {
        return departmentRepository.findAll().stream().map(this::map).toList();
    }

    private void apply(Department department, DepartmentRequest request) {
        department.setName(request.name());
        department.setCode(request.code());
        department.setDescription(request.description());
        department.setActive(request.active());
    }

    private DepartmentResponse map(Department department) {
        return new DepartmentResponse(department.getId(), department.getName(), department.getCode(), department.getDescription(), department.isActive());
    }
}
