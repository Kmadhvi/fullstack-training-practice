package com.hms.scheduler;

import com.hms.repository.AppointmentRepository;
import com.hms.repository.MedicineInventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HmsScheduler {

    private static final Logger log = LoggerFactory.getLogger(HmsScheduler.class);

    private final MedicineInventoryRepository medicineInventoryRepository;
    private final AppointmentRepository appointmentRepository;

    public HmsScheduler(MedicineInventoryRepository medicineInventoryRepository, AppointmentRepository appointmentRepository) {
        this.medicineInventoryRepository = medicineInventoryRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void logDailyOperationalAlerts() {
        int lowStockCount = medicineInventoryRepository.findLowStockItems().size();
        long appointmentCount = appointmentRepository.count();
        log.info("Daily HMS alerts: lowStockItems={}, totalAppointments={}", lowStockCount, appointmentCount);
    }
}
