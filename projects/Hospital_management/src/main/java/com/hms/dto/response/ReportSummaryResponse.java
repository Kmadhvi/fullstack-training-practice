package com.hms.dto.response;

import java.math.BigDecimal;
import java.util.Map;

public record ReportSummaryResponse(
        long totalPatients,
        long totalDoctors,
        long todayAppointments,
        long activeAdmissions,
        BigDecimal totalRevenue,
        BigDecimal outstandingDues,
        Map<String, Long> appointmentsByStatus,
        Map<String, Long> patientsByGender
) {
}
