package com.hms.service.impl;

import com.hms.dto.request.PrescriptionItemRequest;
import com.hms.dto.request.PrescriptionRequest;
import com.hms.dto.response.PrescriptionResponse;
import com.hms.entity.Consultation;
import com.hms.entity.Doctor;
import com.hms.entity.Patient;
import com.hms.entity.Prescription;
import com.hms.entity.PrescriptionItem;
import com.hms.exception.HmsException;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.ConsultationRepository;
import com.hms.repository.DoctorRepository;
import com.hms.repository.PatientRepository;
import com.hms.repository.PrescriptionRepository;
import com.hms.service.AuditService;
import com.hms.service.PrescriptionService;
import com.hms.util.PrescriptionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final ConsultationRepository consultationRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AuditService auditService;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository, ConsultationRepository consultationRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, AuditService auditService) {
        this.prescriptionRepository = prescriptionRepository;
        this.consultationRepository = consultationRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.auditService = auditService;
    }

    @Override
    @Transactional
    public PrescriptionResponse create(PrescriptionRequest request) {
        Consultation consultation = consultationRepository.findById(request.consultationId())
                .orElseThrow(() -> new ResourceNotFoundException("CONSULTATION_NOT_FOUND", "Consultation with id " + request.consultationId() + " not found"));
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResourceNotFoundException("PATIENT_NOT_FOUND", "Patient with id " + request.patientId() + " not found"));
        Doctor doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new ResourceNotFoundException("DOCTOR_NOT_FOUND", "Doctor with id " + request.doctorId() + " not found"));
        if (!consultation.getPatient().getId().equals(patient.getId()) || !consultation.getDoctor().getId().equals(doctor.getId())) {
            throw new HmsException("CONSULTATION_MISMATCH", "Prescription patient and doctor must match the consultation", HttpStatus.BAD_REQUEST);
        }
        Prescription prescription = new Prescription();
        prescription.setConsultation(consultation);
        prescription.setPatient(patient);
        prescription.setDoctor(doctor);
        prescription.setInstructions(request.instructions());
        prescription.setIssuedAt(LocalDateTime.now());
        for (PrescriptionItemRequest itemRequest : request.items()) {
            PrescriptionItem item = new PrescriptionItem();
            item.setPrescription(prescription);
            item.setMedicineName(itemRequest.medicineName());
            item.setDosage(itemRequest.dosage());
            item.setFrequency(itemRequest.frequency());
            item.setDurationDays(itemRequest.durationDays());
            item.setQuantity(itemRequest.quantity());
            item.setInstructions(itemRequest.instructions());
            prescription.getItems().add(item);
        }
        Prescription saved = prescriptionRepository.save(prescription);
        auditService.log("PRESCRIPTION_ISSUED", "Prescription", saved.getId(), "Issued prescription for patient " + patient.getMrn());
        return PrescriptionMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PrescriptionResponse getById(Long id) {
        return PrescriptionMapper.toResponse(findPrescription(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getByPatient(Long patientId) {
        return prescriptionRepository.findByPatientIdOrderByIssuedAtDesc(patientId).stream().map(PrescriptionMapper::toResponse).toList();
    }

    private Prescription findPrescription(Long id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PRESCRIPTION_NOT_FOUND", "Prescription with id " + id + " not found"));
    }
}
