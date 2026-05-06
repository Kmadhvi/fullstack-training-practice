package com.hms.service.impl;

import com.hms.dto.request.DoctorRequest;
import com.hms.dto.response.DoctorResponse;
import com.hms.entity.Department;
import com.hms.entity.Doctor;
import com.hms.entity.User;
import com.hms.enums.UserRole;
import com.hms.exception.DuplicateResourceException;
import com.hms.exception.HmsException;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.DepartmentRepository;
import com.hms.repository.DoctorRepository;
import com.hms.repository.UserRepository;
import com.hms.service.AuditService;
import com.hms.service.DoctorService;
import com.hms.util.DoctorMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final AuditService auditService;

    public DoctorServiceImpl(DoctorRepository doctorRepository, UserRepository userRepository, DepartmentRepository departmentRepository, AuditService auditService) {
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.auditService = auditService;
    }

    @Override
    @Transactional
    public DoctorResponse create(DoctorRequest request) {
        if (doctorRepository.existsByLicenseNumber(request.licenseNumber())) {
            throw new DuplicateResourceException("DOCTOR_LICENSE_EXISTS", "Doctor with license " + request.licenseNumber() + " already exists");
        }
        if (doctorRepository.findByUserId(request.userId()).isPresent()) {
            throw new DuplicateResourceException("DOCTOR_USER_EXISTS", "Doctor profile already exists for user id " + request.userId());
        }
        Doctor doctor = new Doctor();
        apply(doctor, request);
        Doctor saved = doctorRepository.save(doctor);
        auditService.log("DOCTOR_CREATED", "Doctor", saved.getId(), "Created doctor profile");
        return DoctorMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public DoctorResponse update(Long id, DoctorRequest request) {
        Doctor doctor = findDoctor(id);
        doctorRepository.findAll().stream()
                .filter(existing -> existing.getLicenseNumber().equalsIgnoreCase(request.licenseNumber()) && !existing.getId().equals(id))
                .findFirst()
                .ifPresent(existing -> {
                    throw new DuplicateResourceException("DOCTOR_LICENSE_EXISTS", "Doctor with license " + request.licenseNumber() + " already exists");
                });
        apply(doctor, request);
        Doctor saved = doctorRepository.save(doctor);
        auditService.log("DOCTOR_UPDATED", "Doctor", saved.getId(), "Updated doctor profile");
        return DoctorMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorResponse getById(Long id) {
        return DoctorMapper.toResponse(findDoctor(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponse> getAll() {
        return doctorRepository.findAll().stream().map(DoctorMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponse> getAvailableByDepartment(Long departmentId) {
        return doctorRepository.findByDepartmentIdAndAvailableTrue(departmentId).stream().map(DoctorMapper::toResponse).toList();
    }

    private void apply(Doctor doctor, DoctorRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND", "User with id " + request.userId() + " not found"));
        if (user.getRole() != UserRole.ROLE_DOCTOR) {
            throw new HmsException("USER_NOT_DOCTOR", "User must have ROLE_DOCTOR to create a doctor profile", HttpStatus.BAD_REQUEST);
        }
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("DEPARTMENT_NOT_FOUND", "Department with id " + request.departmentId() + " not found"));
        doctor.setUser(user);
        doctor.setDepartment(department);
        doctor.setLicenseNumber(request.licenseNumber().trim());
        doctor.setSpecialization(request.specialization().trim());
        doctor.setQualification(request.qualification().trim());
        doctor.setConsultationFee(request.consultationFee());
        doctor.setAvailable(request.available());
    }

    private Doctor findDoctor(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DOCTOR_NOT_FOUND", "Doctor with id " + id + " not found"));
    }
}
