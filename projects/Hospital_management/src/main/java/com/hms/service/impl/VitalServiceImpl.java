package com.hms.service.impl;

import com.hms.dto.request.VitalRequest;
import com.hms.dto.response.VitalResponse;
import com.hms.entity.Admission;
import com.hms.entity.Patient;
import com.hms.entity.User;
import com.hms.entity.Vital;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.AdmissionRepository;
import com.hms.repository.PatientRepository;
import com.hms.repository.UserRepository;
import com.hms.repository.VitalRepository;
import com.hms.service.AuditService;
import com.hms.service.VitalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VitalServiceImpl implements VitalService {

    private final VitalRepository vitalRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final AdmissionRepository admissionRepository;
    private final AuditService auditService;

    public VitalServiceImpl(VitalRepository vitalRepository, PatientRepository patientRepository, UserRepository userRepository, AdmissionRepository admissionRepository, AuditService auditService) {
        this.vitalRepository = vitalRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.admissionRepository = admissionRepository;
        this.auditService = auditService;
    }

    @Override
    @Transactional
    public VitalResponse record(VitalRequest request) {
        Patient patient = patientRepository.findById(request.patientId()).orElseThrow(() -> new ResourceNotFoundException("PATIENT_NOT_FOUND", "Patient with id " + request.patientId() + " not found"));
        User recordedBy = userRepository.findById(request.recordedBy()).orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND", "User with id " + request.recordedBy() + " not found"));
        Admission admission = request.admissionId() == null ? null : admissionRepository.findById(request.admissionId()).orElseThrow(() -> new ResourceNotFoundException("ADMISSION_NOT_FOUND", "Admission with id " + request.admissionId() + " not found"));
        Vital vital = new Vital();
        vital.setPatient(patient);
        vital.setRecordedBy(recordedBy);
        vital.setAdmission(admission);
        vital.setTemperatureCelsius(request.temperatureCelsius());
        vital.setPulseRate(request.pulseRate());
        vital.setRespiratoryRate(request.respiratoryRate());
        vital.setSystolicBp(request.systolicBp());
        vital.setDiastolicBp(request.diastolicBp());
        vital.setOxygenSaturation(request.oxygenSaturation());
        vital.setWeightKg(request.weightKg());
        vital.setNotes(request.notes());
        vital.setRecordedAt(request.recordedAt() == null ? LocalDateTime.now() : request.recordedAt());
        Vital saved = vitalRepository.save(vital);
        auditService.log("VITAL_RECORDED", "Vital", saved.getId(), "Recorded vitals for patient " + patient.getMrn());
        return map(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VitalResponse> getByPatient(Long patientId) {
        return vitalRepository.findByPatientIdOrderByRecordedAtDesc(patientId).stream().map(this::map).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VitalResponse> getByAdmission(Long admissionId) {
        return vitalRepository.findByAdmissionIdOrderByRecordedAtDesc(admissionId).stream().map(this::map).toList();
    }

    private VitalResponse map(Vital vital) {
        return new VitalResponse(vital.getId(), vital.getPatient().getId(), vital.getPatient().getFirstName() + " " + vital.getPatient().getLastName(), vital.getRecordedBy().getId(), vital.getRecordedBy().getFullName(), vital.getAdmission() == null ? null : vital.getAdmission().getId(), vital.getTemperatureCelsius(), vital.getPulseRate(), vital.getRespiratoryRate(), vital.getSystolicBp(), vital.getDiastolicBp(), vital.getOxygenSaturation(), vital.getWeightKg(), vital.getNotes(), vital.getRecordedAt());
    }
}
