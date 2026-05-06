package com.hms.util;

import com.hms.dto.response.AppointmentResponse;
import com.hms.entity.Appointment;
import com.hms.entity.Department;
import com.hms.entity.Doctor;
import com.hms.entity.Patient;

public final class AppointmentMapper {

    private AppointmentMapper() {
    }

    public static AppointmentResponse toResponse(Appointment appointment) {
        Patient patient = appointment.getPatient();
        Doctor doctor = appointment.getDoctor();
        Department department = doctor.getDepartment();
        return new AppointmentResponse(
                appointment.getId(),
                patient.getId(),
                patient.getFirstName() + " " + patient.getLastName(),
                patient.getMrn(),
                doctor.getId(),
                doctor.getUser().getFullName(),
                department.getName(),
                appointment.getAppointmentType(),
                appointment.getStatus(),
                appointment.getAppointmentAt(),
                appointment.getReason(),
                appointment.getQueueNumber(),
                appointment.isWalkIn(),
                appointment.getCreatedAt(),
                appointment.getUpdatedAt()
        );
    }
}
