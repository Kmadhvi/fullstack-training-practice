package com.portfolio.hospital;

import com.portfolio.hospital.service.HospitalService;

import java.time.LocalDate;

public class HospitalManagementApp {
    public static void main(String[] args) {
        HospitalService service = new HospitalService();

        service.addDoctor(1, "Dr. Mehta", "Cardiology");
        service.addDoctor(2, "Dr. Khan", "Neurology");

        service.addPatient(101, "Aarav", 45, "Hypertension");
        service.addPatient(102, "Sara", 31, "Migraine");

        service.scheduleAppointment(1001, 101, 1, LocalDate.now().plusDays(1));
        service.scheduleAppointment(1002, 102, 2, LocalDate.now().plusDays(2));

        System.out.println("=== Doctors ===");
        service.getDoctors().forEach(System.out::println);

        System.out.println("\n=== Patients ===");
        service.getPatients().forEach(System.out::println);

        System.out.println("\n=== Appointments ===");
        service.getAppointments().forEach(System.out::println);

        System.out.println("\nPortfolio Tip: Next step is to add JDBC + MySQL + REST API to make this production-style.");
    }
}
