package com.portfolio.hospital.model;

import java.time.LocalDate;

public class Appointment {
    private final int appointmentId;
    private final Patient patient;
    private final Doctor doctor;
    private final LocalDate date;

    public Appointment(int appointmentId, Patient patient, Doctor doctor, LocalDate date) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", patient=" + patient.getName() +
                ", doctor=" + doctor.getName() +
                ", date=" + date +
                '}';
    }
}
