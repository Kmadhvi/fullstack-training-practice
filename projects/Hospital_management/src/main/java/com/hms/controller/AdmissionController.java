package com.hms.controller;

import com.hms.dto.request.AdmissionRequest;
import com.hms.dto.request.DischargeRequest;
import com.hms.dto.response.AdmissionResponse;
import com.hms.dto.response.ApiResponse;
import com.hms.service.AdmissionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admissions")
public class AdmissionController {
    private final AdmissionService admissionService;

    public AdmissionController(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST','NURSE')")
    public ResponseEntity<ApiResponse<AdmissionResponse>> admit(@Valid @RequestBody AdmissionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(admissionService.admit(request), "Patient admitted successfully"));
    }

    @PatchMapping("/{id}/discharge")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','NURSE')")
    public ResponseEntity<ApiResponse<AdmissionResponse>> discharge(@PathVariable Long id, @Valid @RequestBody DischargeRequest request) {
        return ResponseEntity.ok(ApiResponse.success(admissionService.discharge(id, request), "Patient discharged successfully"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','NURSE','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<AdmissionResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(admissionService.getById(id)));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','NURSE','RECEPTIONIST','PATIENT')")
    public ResponseEntity<ApiResponse<List<AdmissionResponse>>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(ApiResponse.success(admissionService.getByPatient(patientId)));
    }
}
