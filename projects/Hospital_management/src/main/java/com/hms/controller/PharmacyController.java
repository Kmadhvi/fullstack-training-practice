package com.hms.controller;

import com.hms.dto.request.DispenseRequest;
import com.hms.dto.request.MedicineInventoryRequest;
import com.hms.dto.response.ApiResponse;
import com.hms.dto.response.MedicineInventoryResponse;
import com.hms.dto.response.PrescriptionResponse;
import com.hms.service.PharmacyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacy")
public class PharmacyController {
    private final PharmacyService pharmacyService;

    public PharmacyController(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    @PostMapping("/inventory")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    public ResponseEntity<ApiResponse<MedicineInventoryResponse>> createMedicine(@Valid @RequestBody MedicineInventoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(pharmacyService.createMedicine(request), "Medicine stock created successfully"));
    }

    @PutMapping("/inventory/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    public ResponseEntity<ApiResponse<MedicineInventoryResponse>> updateMedicine(@PathVariable Long id, @Valid @RequestBody MedicineInventoryRequest request) {
        return ResponseEntity.ok(ApiResponse.success(pharmacyService.updateMedicine(id, request), "Medicine stock updated successfully"));
    }

    @GetMapping("/inventory")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST','DOCTOR')")
    public ResponseEntity<ApiResponse<List<MedicineInventoryResponse>>> getInventory() {
        return ResponseEntity.ok(ApiResponse.success(pharmacyService.getInventory()));
    }

    @GetMapping("/inventory/low-stock")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    public ResponseEntity<ApiResponse<List<MedicineInventoryResponse>>> getLowStock() {
        return ResponseEntity.ok(ApiResponse.success(pharmacyService.getLowStock()));
    }

    @PostMapping("/dispense")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    public ResponseEntity<ApiResponse<PrescriptionResponse>> dispense(@Valid @RequestBody DispenseRequest request) {
        return ResponseEntity.ok(ApiResponse.success(pharmacyService.dispensePrescription(request.prescriptionId()), "Prescription dispensed successfully"));
    }
}
