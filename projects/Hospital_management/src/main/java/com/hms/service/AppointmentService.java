package com.hms.service;

import com.hms.dto.request.AppointmentRequest;
import com.hms.dto.response.AppointmentResponse;
import com.hms.enums.AppointmentStatus;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    AppointmentResponse schedule(AppointmentRequest request);

    AppointmentResponse updateStatus(Long id, AppointmentStatus status);

    AppointmentResponse getById(Long id);

    List<AppointmentResponse> getByPatient(Long patientId);

    List<AppointmentResponse> getDoctorCalendar(Long doctorId, LocalDate date);

    List<AppointmentResponse> getWalkInQueue();
}
