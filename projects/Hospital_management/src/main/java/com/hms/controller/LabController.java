package com.hms.controller;

import com.hms.dto.request.LabOrderRequest;
import com.hms.dto.request.LabResultRequest;
import com.hms.dto.response.ApiResponse;
import com.hms.dto.response.LabOrderResponse;
import com.hms.service.LabService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/labs")
public class LabController {
    private final LabService labService;

    public LabController(LabService labService) {
        this.labService = labService;
    }

    @PostMapping("/orders")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public ResponseEntity<ApiResponse<LabOrderResponse>> createOrder(@Valid @RequestBody LabOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(labService.createOrder(request), "Lab order created successfully"));
    }

    @PatchMapping("/items/{itemId}/result")
    @PreAuthorize("hasAnyRole('ADMIN','LAB_TECH')")
    public ResponseEntity<ApiResponse<LabOrderResponse>> updateResult(@PathVariable Long itemId, @Valid @RequestBody LabResultRequest request) {
        return ResponseEntity.ok(ApiResponse.success(labService.updateItemResult(itemId, request), "Lab result updated successfully"));
    }

    @GetMapping("/orders/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','NURSE','LAB_TECH','PATIENT')")
    public ResponseEntity<ApiResponse<LabOrderResponse>> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(labService.getOrder(id)));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','NURSE','LAB_TECH','PATIENT')")
    public ResponseEntity<ApiResponse<List<LabOrderResponse>>> getPatientOrders(@PathVariable Long patientId) {
        return ResponseEntity.ok(ApiResponse.success(labService.getPatientOrders(patientId)));
    }
}
