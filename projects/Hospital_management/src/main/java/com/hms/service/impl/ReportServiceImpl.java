package com.hms.service.impl;

import com.hms.dto.response.ReportSummaryResponse;
import com.hms.entity.Appointment;
import com.hms.entity.Bill;
import com.hms.entity.Patient;
import com.hms.enums.AdmissionStatus;
import com.hms.repository.AdmissionRepository;
import com.hms.repository.AppointmentRepository;
import com.hms.repository.BillRepository;
import com.hms.repository.DoctorRepository;
import com.hms.repository.PatientRepository;
import com.hms.service.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final AdmissionRepository admissionRepository;
    private final BillRepository billRepository;

    public ReportServiceImpl(PatientRepository patientRepository, DoctorRepository doctorRepository, AppointmentRepository appointmentRepository, AdmissionRepository admissionRepository, BillRepository billRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.admissionRepository = admissionRepository;
        this.billRepository = billRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ReportSummaryResponse summary() {
        LocalDate today = LocalDate.now();
        long todayAppointments = appointmentRepository.findAll().stream()
                .filter(a -> !a.getAppointmentAt().isBefore(today.atStartOfDay()) && !a.getAppointmentAt().isAfter(today.atTime(LocalTime.MAX)))
                .count();
        BigDecimal totalRevenue = billRepository.findAll().stream().map(Bill::getPaidAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal dues = billRepository.findAll().stream().map(b -> b.getTotalAmount().subtract(b.getPaidAmount())).reduce(BigDecimal.ZERO, BigDecimal::add);
        Map<String, Long> appointmentsByStatus = appointmentRepository.findAll().stream().collect(Collectors.groupingBy(a -> a.getStatus().name(), Collectors.counting()));
        Map<String, Long> patientsByGender = patientRepository.findAll().stream().collect(Collectors.groupingBy(p -> p.getGender().name(), Collectors.counting()));
        return new ReportSummaryResponse(
                patientRepository.count(),
                doctorRepository.count(),
                todayAppointments,
                admissionRepository.findByStatus(AdmissionStatus.ADMITTED).size(),
                totalRevenue,
                dues,
                appointmentsByStatus,
                patientsByGender
        );
    }
}
