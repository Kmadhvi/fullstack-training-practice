package com.hms.repository;

import com.hms.entity.Vital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VitalRepository extends JpaRepository<Vital, Long> {
    List<Vital> findByPatientIdOrderByRecordedAtDesc(Long patientId);

    List<Vital> findByAdmissionIdOrderByRecordedAtDesc(Long admissionId);
}
