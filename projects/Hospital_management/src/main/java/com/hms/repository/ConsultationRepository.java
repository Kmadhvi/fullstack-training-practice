package com.hms.repository;

import com.hms.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByPatientIdOrderByCreatedAtDesc(Long patientId);

    List<Consultation> findByDoctorIdOrderByCreatedAtDesc(Long doctorId);
}
