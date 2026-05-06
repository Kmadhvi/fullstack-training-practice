package com.hms.repository;

import com.hms.entity.Admission;
import com.hms.enums.AdmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {
    List<Admission> findByPatientIdOrderByAdmittedAtDesc(Long patientId);

    List<Admission> findByStatus(AdmissionStatus status);
}
