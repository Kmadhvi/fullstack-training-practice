package com.hms.service;

import com.hms.dto.request.MedicineInventoryRequest;
import com.hms.dto.response.MedicineInventoryResponse;
import com.hms.dto.response.PrescriptionResponse;

import java.util.List;

public interface PharmacyService {
    MedicineInventoryResponse createMedicine(MedicineInventoryRequest request);

    MedicineInventoryResponse updateMedicine(Long id, MedicineInventoryRequest request);

    List<MedicineInventoryResponse> getInventory();

    List<MedicineInventoryResponse> getLowStock();

    PrescriptionResponse dispensePrescription(Long prescriptionId);
}
