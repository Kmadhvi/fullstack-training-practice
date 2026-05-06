package com.hms.repository;

import com.hms.entity.Appointment;
import com.hms.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctorIdAndAppointmentAtBetween(Long doctorId, LocalDateTime start, LocalDateTime end);

    List<Appointment> findByPatientIdOrderByAppointmentAtDesc(Long patientId);

    List<Appointment> findByStatusOrderByAppointmentAtAsc(AppointmentStatus status);
}
