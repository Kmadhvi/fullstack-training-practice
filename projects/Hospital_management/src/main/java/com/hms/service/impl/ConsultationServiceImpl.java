package com.hms.service.impl;

import com.hms.dto.request.ConsultationRequest;
import com.hms.dto.response.ConsultationResponse;
import com.hms.entity.Appointment;
import com.hms.entity.Consultation;
import com.hms.entity.Doctor;
import com.hms.entity.Patient;
import com.hms.enums.AppointmentStatus;
import com.hms.exception.HmsException;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.AppointmentRepository;
import com.hms.repository.ConsultationRepository;
import com.hms.repository.DoctorRepository;
import com.hms.repository.PatientRepository;
import com.hms.service.AuditService;
import com.hms.service.ConsultationService;
import com.hms.util.ConsultationMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AuditService auditService;

    public ConsultationServiceImpl(
            ConsultationRepository consultationRepository,
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            AuditService auditService
    ) {
        this.consultationRepository = consultationRepository;
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.auditService = auditService;
    }

    @Override
    @Transactional
    public ConsultationResponse create(ConsultationRequest request) {
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResourceNotFoundException("PATIENT_NOT_FOUND", "Patient with id " + request.patientId() + " not found"));
        Doctor doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new ResourceNotFoundException("DOCTOR_NOT_FOUND", "Doctor with id " + request.doctorId() + " not found"));

        Appointment appointment = null;
        if (request.appointmentId() != null) {
            appointment = appointmentRepository.findById(request.appointmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("APPOINTMENT_NOT_FOUND", "Appointment with id " + request.appointmentId() + " not found"));
            if (!appointment.getPatient().getId().equals(patient.getId()) || !appointment.getDoctor().getId().equals(doctor.getId())) {
                throw new HmsException("APPOINTMENT_MISMATCH", "Appointment does not belong to the supplied patient and doctor", HttpStatus.BAD_REQUEST);
            }
            appointment.setStatus(AppointmentStatus.IN_CONSULTATION);
        }

        validateFollowUpDate(request.followUpDate());
        Consultation consultation = new Consultation();
        apply(consultation, request, appointment, patient, doctor);
        Consultation saved = consultationRepository.save(consultation);
        auditService.log("CONSULTATION_CREATED", "Consultation", saved.getId(), "Created consultation for patient " + patient.getMrn());
        return ConsultationMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ConsultationResponse update(Long id, ConsultationRequest request) {
        Consultation consultation = findConsultation(id);
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResourceNotFoundException("PATIENT_NOT_FOUND", "Patient with id " + request.patientId() + " not found"));
        Doctor doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new ResourceNotFoundException("DOCTOR_NOT_FOUND", "Doctor with id " + request.doctorId() + " not found"));
        Appointment appointment = null;
        if (request.appointmentId() != null) {
            appointment = appointmentRepository.findById(request.appointmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("APPOINTMENT_NOT_FOUND", "Appointment with id " + request.appointmentId() + " not found"));
        }
        validateFollowUpDate(request.followUpDate());
        apply(consultation, request, appointment, patient, doctor);
        Consultation saved = consultationRepository.save(consultation);
        auditService.log("CONSULTATION_UPDATED", "Consultation", saved.getId(), "Updated consultation notes");
        return ConsultationMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ConsultationResponse getById(Long id) {
        return ConsultationMapper.toResponse(findConsultation(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsultationResponse> getByPatient(Long patientId) {
        return consultationRepository.findByPatientIdOrderByCreatedAtDesc(patientId)
                .stream()
                .map(ConsultationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsultationResponse> getByDoctor(Long doctorId) {
        return consultationRepository.findByDoctorIdOrderByCreatedAtDesc(doctorId)
                .stream()
                .map(ConsultationMapper::toResponse)
                .toList();
    }

    private void apply(Consultation consultation, ConsultationRequest request, Appointment appointment, Patient patient, Doctor doctor) {
        consultation.setAppointment(appointment);
        consultation.setPatient(patient);
        consultation.setDoctor(doctor);
        consultation.setSymptoms(request.symptoms());
        consultation.setDiagnosis(request.diagnosis());
        consultation.setNotes(request.notes());
        consultation.setFollowUpDate(request.followUpDate());
    }

    private void validateFollowUpDate(LocalDate followUpDate) {
        if (followUpDate != null && followUpDate.isBefore(LocalDate.now())) {
            throw new HmsException("INVALID_FOLLOW_UP_DATE", "Follow-up date cannot be in the past", HttpStatus.BAD_REQUEST);
        }
    }

    private Consultation findConsultation(Long id) {
        return consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CONSULTATION_NOT_FOUND", "Consultation with id " + id + " not found"));
    }
}
