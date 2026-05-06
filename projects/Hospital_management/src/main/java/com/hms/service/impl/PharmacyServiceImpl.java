package com.hms.service.impl;

import com.hms.dto.request.MedicineInventoryRequest;
import com.hms.dto.response.MedicineInventoryResponse;
import com.hms.dto.response.PrescriptionResponse;
import com.hms.entity.MedicineInventory;
import com.hms.entity.Prescription;
import com.hms.enums.PrescriptionStatus;
import com.hms.exception.HmsException;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.MedicineInventoryRepository;
import com.hms.repository.PrescriptionRepository;
import com.hms.service.AuditService;
import com.hms.service.PharmacyService;
import com.hms.util.PrescriptionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class PharmacyServiceImpl implements PharmacyService {

    private final MedicineInventoryRepository medicineInventoryRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final AuditService auditService;

    public PharmacyServiceImpl(MedicineInventoryRepository medicineInventoryRepository, PrescriptionRepository prescriptionRepository, AuditService auditService) {
        this.medicineInventoryRepository = medicineInventoryRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.auditService = auditService;
    }

    @Override
    @Transactional
    public MedicineInventoryResponse createMedicine(MedicineInventoryRequest request) {
        MedicineInventory medicine = new MedicineInventory();
        apply(medicine, request);
        MedicineInventory saved = medicineInventoryRepository.save(medicine);
        auditService.log("MEDICINE_CREATED", "MedicineInventory", saved.getId(), "Created medicine stock item");
        return map(saved);
    }

    @Override
    @Transactional
    public MedicineInventoryResponse updateMedicine(Long id, MedicineInventoryRequest request) {
        MedicineInventory medicine = medicineInventoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("MEDICINE_NOT_FOUND", "Medicine with id " + id + " not found"));
        apply(medicine, request);
        MedicineInventory saved = medicineInventoryRepository.save(medicine);
        auditService.log("MEDICINE_UPDATED", "MedicineInventory", saved.getId(), "Updated medicine stock item");
        return map(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicineInventoryResponse> getInventory() {
        return medicineInventoryRepository.findAll().stream().map(this::map).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicineInventoryResponse> getLowStock() {
        return medicineInventoryRepository.findLowStockItems().stream().map(this::map).toList();
    }

    @Override
    @Transactional
    public PrescriptionResponse dispensePrescription(Long prescriptionId) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("PRESCRIPTION_NOT_FOUND", "Prescription with id " + prescriptionId + " not found"));
        if (prescription.getStatus() == PrescriptionStatus.DISPENSED || prescription.getStatus() == PrescriptionStatus.CANCELLED) {
            throw new HmsException("PRESCRIPTION_NOT_DISPENSABLE", "Prescription cannot be dispensed in status " + prescription.getStatus(), HttpStatus.BAD_REQUEST);
        }
        prescription.getItems().forEach(item -> {
            MedicineInventory stock = medicineInventoryRepository.findAll().stream()
                    .filter(m -> m.getMedicineName().equalsIgnoreCase(item.getMedicineName()))
                    .filter(m -> m.getQuantityAvailable() >= item.getQuantity())
                    .min(Comparator.comparing(MedicineInventory::getExpiryDate))
                    .orElseThrow(() -> new HmsException("INSUFFICIENT_STOCK", "Insufficient stock for " + item.getMedicineName(), HttpStatus.CONFLICT));
            stock.setQuantityAvailable(stock.getQuantityAvailable() - item.getQuantity());
        });
        prescription.setStatus(PrescriptionStatus.DISPENSED);
        Prescription saved = prescriptionRepository.save(prescription);
        auditService.log("PRESCRIPTION_DISPENSED", "Prescription", saved.getId(), "Dispensed prescription");
        return PrescriptionMapper.toResponse(saved);
    }

    private void apply(MedicineInventory medicine, MedicineInventoryRequest request) {
        medicine.setMedicineName(request.medicineName());
        medicine.setBatchNumber(request.batchNumber());
        medicine.setManufacturer(request.manufacturer());
        medicine.setExpiryDate(request.expiryDate());
        medicine.setQuantityAvailable(request.quantityAvailable());
        medicine.setReorderLevel(request.reorderLevel());
        medicine.setUnitPrice(request.unitPrice());
    }

    private MedicineInventoryResponse map(MedicineInventory medicine) {
        return new MedicineInventoryResponse(medicine.getId(), medicine.getMedicineName(), medicine.getBatchNumber(), medicine.getManufacturer(), medicine.getExpiryDate(), medicine.getQuantityAvailable(), medicine.getReorderLevel(), medicine.getUnitPrice(), medicine.getQuantityAvailable() <= medicine.getReorderLevel());
    }
}
