package com.hms.repository;

import com.hms.entity.Bill;
import com.hms.enums.BillStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByBillNumber(String billNumber);

    List<Bill> findByPatientIdOrderByCreatedAtDesc(Long patientId);

    List<Bill> findByStatus(BillStatus status);
}
