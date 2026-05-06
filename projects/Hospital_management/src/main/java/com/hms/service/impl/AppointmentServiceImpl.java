package com.hms.service.impl;

import com.hms.dto.request.AppointmentRequest;
import com.hms.dto.response.AppointmentResponse;
import com.hms.entity.Appointment;
import com.hms.entity.Doctor;
import com.hms.entity.Patient;
import com.hms.enums.AppointmentStatus;
import com.hms.exception.HmsException;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.AppointmentRepository;
import com.hms.repository.DoctorRepository;
import com.hms.repository.PatientRepository;
import com.hms.service.AppointmentService;
import com.hms.service.AuditService;
import com.hms.util.AppointmentMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AuditService auditService;

    public AppointmentServiceImpl(
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            AuditService auditService
    ) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.auditService = auditService;
    }

    @Override
    @Transactional
    public AppointmentResponse schedule(AppointmentRequest request) {
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResourceNotFoundException("PATIENT_NOT_FOUND", "Patient with id " + request.patientId() + " not found"));
        Doctor doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new ResourceNotFoundException("DOCTOR_NOT_FOUND", "Doctor with id " + request.doctorId() + " not found"));
        if (!doctor.isAvailable()) {
            throw new HmsException("DOCTOR_UNAVAILABLE", "Doctor is currently unavailable for appointments", HttpStatus.BAD_REQUEST);
        }
        ensureSlotAvailable(doctor.getId(), request.appointmentAt());

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentType(request.appointmentType());
        appointment.setAppointmentAt(request.appointmentAt());
        appointment.setReason(request.reason());
        appointment.setWalkIn(request.walkIn());
        appointment.setStatus(request.walkIn() ? AppointmentStatus.CHECKED_IN : AppointmentStatus.SCHEDULED);
        if (request.walkIn()) {
            appointment.setQueueNumber(nextQueueNumber(request.appointmentAt().toLocalDate()));
        }

        Appointment saved = appointmentRepository.save(appointment);
        auditService.log("APPOINTMENT_SCHEDULED", "Appointment", saved.getId(), "Scheduled appointment for patient " + patient.getMrn());
        return AppointmentMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public AppointmentResponse updateStatus(Long id, AppointmentStatus status) {
        Appointment appointment = findAppointment(id);
        validateStatusTransition(appointment.getStatus(), status);
        appointment.setStatus(status);
        Appointment saved = appointmentRepository.save(appointment);
        auditService.log("APPOINTMENT_STATUS_UPDATED", "Appointment", saved.getId(), "Status changed to " + status);
        return AppointmentMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponse getById(Long id) {
        return AppointmentMapper.toResponse(findAppointment(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getByPatient(Long patientId) {
        return appointmentRepository.findByPatientIdOrderByAppointmentAtDesc(patientId)
                .stream()
                .map(AppointmentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getDoctorCalendar(Long doctorId, LocalDate date) {
        LocalDate targetDate = date == null ? LocalDate.now() : date;
        LocalDateTime start = targetDate.atStartOfDay();
        LocalDateTime end = targetDate.atTime(LocalTime.MAX);
        return appointmentRepository.findByDoctorIdAndAppointmentAtBetween(doctorId, start, end)
                .stream()
                .sorted(Comparator.comparing(Appointment::getAppointmentAt))
                .map(AppointmentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getWalkInQueue() {
        return appointmentRepository.findByStatusOrderByAppointmentAtAsc(AppointmentStatus.CHECKED_IN)
                .stream()
                .filter(Appointment::isWalkIn)
                .sorted(Comparator.comparing(Appointment::getQueueNumber, Comparator.nullsLast(Integer::compareTo)))
                .map(AppointmentMapper::toResponse)
                .toList();
    }

    private void ensureSlotAvailable(Long doctorId, LocalDateTime appointmentAt) {
        LocalDateTime start = appointmentAt.minusMinutes(29);
        LocalDateTime end = appointmentAt.plusMinutes(29);
        boolean conflict = appointmentRepository.findByDoctorIdAndAppointmentAtBetween(doctorId, start, end)
                .stream()
                .anyMatch(existing -> existing.getStatus() != AppointmentStatus.CANCELLED && existing.getStatus() != AppointmentStatus.NO_SHOW);
        if (conflict) {
            throw new HmsException("APPOINTMENT_SLOT_UNAVAILABLE", "Doctor already has an appointment within this time slot", HttpStatus.CONFLICT);
        }
    }

    private int nextQueueNumber(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);
        return appointmentRepository.findAll().stream()
                .filter(Appointment::isWalkIn)
                .filter(appointment -> !appointment.getAppointmentAt().isBefore(start) && !appointment.getAppointmentAt().isAfter(end))
                .map(Appointment::getQueueNumber)
                .filter(java.util.Objects::nonNull)
                .max(Integer::compareTo)
                .orElse(0) + 1;
    }

    private void validateStatusTransition(AppointmentStatus current, AppointmentStatus next) {
        if (current == AppointmentStatus.CANCELLED || current == AppointmentStatus.COMPLETED || current == AppointmentStatus.NO_SHOW) {
            throw new HmsException("APPOINTMENT_STATUS_FINAL", "Cannot change status after appointment is " + current, HttpStatus.BAD_REQUEST);
        }
        if (next == AppointmentStatus.SCHEDULED && current != AppointmentStatus.SCHEDULED) {
            throw new HmsException("INVALID_APPOINTMENT_STATUS", "Cannot move appointment back to scheduled", HttpStatus.BAD_REQUEST);
        }
    }

    private Appointment findAppointment(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("APPOINTMENT_NOT_FOUND", "Appointment with id " + id + " not found"));
    }
}
