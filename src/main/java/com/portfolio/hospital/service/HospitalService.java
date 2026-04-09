package com.portfolio.hospital.service;

import com.portfolio.hospital.model.Appointment;
import com.portfolio.hospital.model.Doctor;
import com.portfolio.hospital.model.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HospitalService {
    private final List<Patient> patients = new ArrayList<>();
    private final List<Doctor> doctors = new ArrayList<>();
    private final List<Appointment> appointments = new ArrayList<>();

    public Patient addPatient(int id, String name, int age, String disease) {
        Patient patient = new Patient(id, name, age, disease);
        patients.add(patient);
        return patient;
    }

    public Doctor addDoctor(int id, String name, String specialty) {
        Doctor doctor = new Doctor(id, name, specialty);
        doctors.add(doctor);
        return doctor;
    }

    public Appointment scheduleAppointment(int appointmentId, int patientId, int doctorId, LocalDate date) {
        Optional<Patient> patientOpt = patients.stream().filter(p -> p.getId() == patientId).findFirst();
        Optional<Doctor> doctorOpt = doctors.stream().filter(d -> d.getId() == doctorId).findFirst();

        if (patientOpt.isEmpty() || doctorOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid patient or doctor ID for appointment scheduling");
        }

        Appointment appointment = new Appointment(appointmentId, patientOpt.get(), doctorOpt.get(), date);
        appointments.add(appointment);
        return appointment;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }
}
