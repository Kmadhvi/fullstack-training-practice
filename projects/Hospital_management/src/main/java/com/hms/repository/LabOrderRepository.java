package com.hms.repository;

import com.hms.entity.LabOrder;
import com.hms.enums.LabOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabOrderRepository extends JpaRepository<LabOrder, Long> {
    List<LabOrder> findByPatientIdOrderByOrderedAtDesc(Long patientId);

    List<LabOrder> findByStatus(LabOrderStatus status);
}
