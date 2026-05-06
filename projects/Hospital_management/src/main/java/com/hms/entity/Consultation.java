package com.hms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "consultations")
public class Consultation extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Lob
    @jakarta.persistence.Column(columnDefinition = "TEXT")
    private String symptoms;

    @Lob
    @jakarta.persistence.Column(columnDefinition = "TEXT")
    private String diagnosis;

    @Lob
    @jakarta.persistence.Column(columnDefinition = "TEXT")
    private String notes;

    private LocalDate followUpDate;
}
