package com.hms.repository;

import com.hms.entity.Prescription;
import com.hms.enums.PrescriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByPatientIdOrderByIssuedAtDesc(Long patientId);

    List<Prescription> findByStatus(PrescriptionStatus status);
}
