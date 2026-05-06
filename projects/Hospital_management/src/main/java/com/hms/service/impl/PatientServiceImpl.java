package com.hms.service.impl;

import com.hms.dto.request.PatientRequest;
import com.hms.dto.response.PatientResponse;
import com.hms.entity.Patient;
import com.hms.entity.User;
import com.hms.enums.BloodGroup;
import com.hms.exception.DuplicateResourceException;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.PatientRepository;
import com.hms.repository.UserRepository;
import com.hms.service.AuditService;
import com.hms.service.PatientService;
import com.hms.util.PatientMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final AuditService auditService;

    public PatientServiceImpl(PatientRepository patientRepository, UserRepository userRepository, AuditService auditService) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.auditService = auditService;
    }

    @Override
    @Transactional
    public PatientResponse create(PatientRequest request) {
        if (patientRepository.existsByMrn(request.mrn())) {
            throw new DuplicateResourceException("PATIENT_MRN_EXISTS", "Patient with MRN " + request.mrn() + " already exists");
        }
        Patient patient = new Patient();
        apply(patient, request);
        Patient saved = patientRepository.save(patient);
        auditService.log("PATIENT_CREATED", "Patient", saved.getId(), "Registered patient " + saved.getMrn());
        return PatientMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public PatientResponse update(Long id, PatientRequest request) {
        Patient patient = findPatient(id);
        patientRepository.findByMrn(request.mrn())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new DuplicateResourceException("PATIENT_MRN_EXISTS", "Patient with MRN " + request.mrn() + " already exists");
                });
        apply(patient, request);
        Patient saved = patientRepository.save(patient);
        auditService.log("PATIENT_UPDATED", "Patient", saved.getId(), "Updated patient profile");
        return PatientMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponse getById(Long id) {
        return PatientMapper.toResponse(findPatient(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> search(String query) {
        if (query == null || query.isBlank()) {
            return getAll();
        }
        return patientRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrPhoneContaining(query, query, query)
                .stream()
                .map(PatientMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> getAll() {
        return patientRepository.findAll().stream().map(PatientMapper::toResponse).toList();
    }

    private void apply(Patient patient, PatientRequest request) {
        User user = null;
        if (request.userId() != null) {
            user = userRepository.findById(request.userId())
                    .orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND", "User with id " + request.userId() + " not found"));
        }
        patient.setUser(user);
        patient.setMrn(request.mrn().trim());
        patient.setFirstName(request.firstName().trim());
        patient.setLastName(request.lastName().trim());
        patient.setGender(request.gender());
        patient.setDateOfBirth(request.dateOfBirth());
        patient.setBloodGroup(request.bloodGroup() == null ? BloodGroup.UNKNOWN : request.bloodGroup());
        patient.setPhone(request.phone().trim());
        patient.setEmail(request.email() == null ? null : request.email().trim().toLowerCase());
        patient.setAddress(request.address());
        patient.setEmergencyContactName(request.emergencyContactName());
        patient.setEmergencyContactPhone(request.emergencyContactPhone());
        patient.setAllergies(request.allergies());
        patient.setMedicalHistory(request.medicalHistory());
    }

    private Patient findPatient(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PATIENT_NOT_FOUND", "Patient with id " + id + " not found"));
    }
}
