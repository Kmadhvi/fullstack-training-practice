package com.hms.service.impl;

import com.hms.dto.request.NursingNoteRequest;
import com.hms.dto.response.NursingNoteResponse;
import com.hms.entity.Admission;
import com.hms.entity.NursingNote;
import com.hms.entity.Patient;
import com.hms.entity.User;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.AdmissionRepository;
import com.hms.repository.NursingNoteRepository;
import com.hms.repository.PatientRepository;
import com.hms.repository.UserRepository;
import com.hms.service.AuditService;
import com.hms.service.NursingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NursingServiceImpl implements NursingService {

    private final NursingNoteRepository nursingNoteRepository;
    private final AdmissionRepository admissionRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final AuditService auditService;

    public NursingServiceImpl(NursingNoteRepository nursingNoteRepository, AdmissionRepository admissionRepository, PatientRepository patientRepository, UserRepository userRepository, AuditService auditService) {
        this.nursingNoteRepository = nursingNoteRepository;
        this.admissionRepository = admissionRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.auditService = auditService;
    }

    @Override
    @Transactional
    public NursingNoteResponse addNote(NursingNoteRequest request) {
        Admission admission = admissionRepository.findById(request.admissionId()).orElseThrow(() -> new ResourceNotFoundException("ADMISSION_NOT_FOUND", "Admission with id " + request.admissionId() + " not found"));
        Patient patient = patientRepository.findById(request.patientId()).orElseThrow(() -> new ResourceNotFoundException("PATIENT_NOT_FOUND", "Patient with id " + request.patientId() + " not found"));
        User nurse = userRepository.findById(request.nurseId()).orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND", "User with id " + request.nurseId() + " not found"));
        NursingNote note = new NursingNote();
        note.setAdmission(admission);
        note.setPatient(patient);
        note.setNurse(nurse);
        note.setNote(request.note());
        note.setRecordedAt(request.recordedAt() == null ? LocalDateTime.now() : request.recordedAt());
        NursingNote saved = nursingNoteRepository.save(note);
        auditService.log("NURSING_NOTE_ADDED", "NursingNote", saved.getId(), "Added nursing note");
        return map(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NursingNoteResponse> getByAdmission(Long admissionId) {
        return nursingNoteRepository.findByAdmissionIdOrderByRecordedAtDesc(admissionId).stream().map(this::map).toList();
    }

    private NursingNoteResponse map(NursingNote note) {
        return new NursingNoteResponse(note.getId(), note.getAdmission().getId(), note.getPatient().getId(), note.getPatient().getFirstName() + " " + note.getPatient().getLastName(), note.getNurse().getId(), note.getNurse().getFullName(), note.getNote(), note.getRecordedAt());
    }
}
