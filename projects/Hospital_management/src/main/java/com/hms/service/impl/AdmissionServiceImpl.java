package com.hms.service.impl;

import com.hms.dto.request.AdmissionRequest;
import com.hms.dto.request.DischargeRequest;
import com.hms.dto.response.AdmissionResponse;
import com.hms.entity.Admission;
import com.hms.entity.Doctor;
import com.hms.entity.Patient;
import com.hms.enums.AdmissionStatus;
import com.hms.exception.HmsException;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.AdmissionRepository;
import com.hms.repository.DoctorRepository;
import com.hms.repository.PatientRepository;
import com.hms.service.AdmissionService;
import com.hms.service.AuditService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdmissionServiceImpl implements AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AuditService auditService;

    public AdmissionServiceImpl(AdmissionRepository admissionRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, AuditService auditService) {
        this.admissionRepository = admissionRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.auditService = auditService;
    }

    @Override
    @Transactional
    public AdmissionResponse admit(AdmissionRequest request) {
        Patient patient = patientRepository.findById(request.patientId()).orElseThrow(() -> new ResourceNotFoundException("PATIENT_NOT_FOUND", "Patient with id " + request.patientId() + " not found"));
        Doctor doctor = doctorRepository.findById(request.doctorId()).orElseThrow(() -> new ResourceNotFoundException("DOCTOR_NOT_FOUND", "Doctor with id " + request.doctorId() + " not found"));
        Admission admission = new Admission();
        admission.setPatient(patient);
        admission.setDoctor(doctor);
        admission.setWard(request.ward());
        admission.setBedNumber(request.bedNumber());
        admission.setAdmittedAt(request.admittedAt() == null ? LocalDateTime.now() : request.admittedAt());
        admission.setDiagnosis(request.diagnosis());
        Admission saved = admissionRepository.save(admission);
        auditService.log("PATIENT_ADMITTED", "Admission", saved.getId(), "Admitted patient " + patient.getMrn());
        return map(saved);
    }

    @Override
    @Transactional
    public AdmissionResponse discharge(Long id, DischargeRequest request) {
        Admission admission = findAdmission(id);
        if (admission.getStatus() == AdmissionStatus.DISCHARGED) {
            throw new HmsException("ALREADY_DISCHARGED", "Admission is already discharged", HttpStatus.BAD_REQUEST);
        }
        admission.setStatus(AdmissionStatus.DISCHARGED);
        admission.setDischargedAt(request.dischargedAt() == null ? LocalDateTime.now() : request.dischargedAt());
        admission.setDischargeSummary(request.dischargeSummary());
        Admission saved = admissionRepository.save(admission);
        auditService.log("PATIENT_DISCHARGED", "Admission", saved.getId(), "Discharged patient");
        return map(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AdmissionResponse getById(Long id) {
        return map(findAdmission(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdmissionResponse> getByPatient(Long patientId) {
        return admissionRepository.findByPatientIdOrderByAdmittedAtDesc(patientId).stream().map(this::map).toList();
    }

    private Admission findAdmission(Long id) {
        return admissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ADMISSION_NOT_FOUND", "Admission with id " + id + " not found"));
    }

    private AdmissionResponse map(Admission a) {
        return new AdmissionResponse(a.getId(), a.getPatient().getId(), a.getPatient().getFirstName() + " " + a.getPatient().getLastName(), a.getDoctor().getId(), a.getDoctor().getUser().getFullName(), a.getStatus(), a.getWard(), a.getBedNumber(), a.getAdmittedAt(), a.getDischargedAt(), a.getDiagnosis(), a.getDischargeSummary());
    }
}
