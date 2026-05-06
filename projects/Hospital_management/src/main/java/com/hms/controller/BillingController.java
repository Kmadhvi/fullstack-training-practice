package com.hms.controller;

import com.hms.dto.request.BillRequest;
import com.hms.dto.request.PaymentRequest;
import com.hms.dto.response.ApiResponse;
import com.hms.dto.response.BillResponse;
import com.hms.service.BillingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billing")
public class BillingController {
    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping("/bills")
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<BillResponse>> create(@Valid @RequestBody BillRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(billingService.create(request), "Bill generated successfully"));
    }

    @PostMapping("/bills/{billId}/payments")
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<BillResponse>> recordPayment(@PathVariable Long billId, @Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(ApiResponse.success(billingService.recordPayment(billId, request), "Payment recorded successfully"));
    }

    @GetMapping("/bills/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST','PATIENT')")
    public ResponseEntity<ApiResponse<BillResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(billingService.getById(id)));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST','PATIENT')")
    public ResponseEntity<ApiResponse<List<BillResponse>>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(ApiResponse.success(billingService.getByPatient(patientId)));
    }
}
