package com.hms.controller;

import com.hms.dto.request.VitalRequest;
import com.hms.dto.response.ApiResponse;
import com.hms.dto.response.VitalResponse;
import com.hms.service.VitalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vitals")
public class VitalController {
    private final VitalService vitalService;

    public VitalController(VitalService vitalService) {
        this.vitalService = vitalService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','NURSE','DOCTOR')")
    public ResponseEntity<ApiResponse<VitalResponse>> record(@Valid @RequestBody VitalRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(vitalService.record(request), "Vitals recorded successfully"));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','NURSE','DOCTOR','PATIENT')")
    public ResponseEntity<ApiResponse<List<VitalResponse>>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(ApiResponse.success(vitalService.getByPatient(patientId)));
    }

    @GetMapping("/admission/{admissionId}")
    @PreAuthorize("hasAnyRole('ADMIN','NURSE','DOCTOR')")
    public ResponseEntity<ApiResponse<List<VitalResponse>>> getByAdmission(@PathVariable Long admissionId) {
        return ResponseEntity.ok(ApiResponse.success(vitalService.getByAdmission(admissionId)));
    }
}
