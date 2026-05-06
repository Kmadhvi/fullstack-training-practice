package com.hms.service.impl;

import com.hms.dto.request.BillItemRequest;
import com.hms.dto.request.BillRequest;
import com.hms.dto.request.PaymentRequest;
import com.hms.dto.response.BillItemResponse;
import com.hms.dto.response.BillResponse;
import com.hms.entity.Bill;
import com.hms.entity.BillItem;
import com.hms.entity.Patient;
import com.hms.enums.BillStatus;
import com.hms.exception.HmsException;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.BillRepository;
import com.hms.repository.PatientRepository;
import com.hms.service.AuditService;
import com.hms.service.BillingService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BillingServiceImpl implements BillingService {

    private final BillRepository billRepository;
    private final PatientRepository patientRepository;
    private final AuditService auditService;

    public BillingServiceImpl(BillRepository billRepository, PatientRepository patientRepository, AuditService auditService) {
        this.billRepository = billRepository;
        this.patientRepository = patientRepository;
        this.auditService = auditService;
    }

    @Override
    @Transactional
    public BillResponse create(BillRequest request) {
        Patient patient = patientRepository.findById(request.patientId()).orElseThrow(() -> new ResourceNotFoundException("PATIENT_NOT_FOUND", "Patient with id " + request.patientId() + " not found"));
        Bill bill = new Bill();
        bill.setPatient(patient);
        bill.setBillNumber("BILL-" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()));
        bill.setDiscount(request.discount() == null ? BigDecimal.ZERO : request.discount());
        bill.setTax(request.tax() == null ? BigDecimal.ZERO : request.tax());
        bill.setIssuedAt(LocalDateTime.now());
        bill.setStatus(BillStatus.ISSUED);
        for (BillItemRequest itemRequest : request.items()) {
            BillItem item = new BillItem();
            item.setBill(bill);
            item.setItemType(itemRequest.itemType());
            item.setDescription(itemRequest.description());
            item.setQuantity(itemRequest.quantity());
            item.setUnitPrice(itemRequest.unitPrice());
            item.setAmount(itemRequest.unitPrice().multiply(BigDecimal.valueOf(itemRequest.quantity())));
            bill.getItems().add(item);
        }
        recalculate(bill);
        Bill saved = billRepository.save(bill);
        auditService.log("BILL_CREATED", "Bill", saved.getId(), "Created invoice " + saved.getBillNumber());
        return map(saved);
    }

    @Override
    @Transactional
    public BillResponse recordPayment(Long billId, PaymentRequest request) {
        Bill bill = billRepository.findById(billId).orElseThrow(() -> new ResourceNotFoundException("BILL_NOT_FOUND", "Bill with id " + billId + " not found"));
        if (bill.getStatus() == BillStatus.CANCELLED) {
            throw new HmsException("BILL_CANCELLED", "Cannot record payment against a cancelled bill", HttpStatus.BAD_REQUEST);
        }
        bill.setPaidAmount(bill.getPaidAmount().add(request.amount()));
        if (bill.getPaidAmount().compareTo(bill.getTotalAmount()) >= 0) {
            bill.setPaidAmount(bill.getTotalAmount());
            bill.setStatus(BillStatus.PAID);
        } else {
            bill.setStatus(BillStatus.PARTIALLY_PAID);
        }
        auditService.log("PAYMENT_RECORDED", "Bill", bill.getId(), "Payment recorded");
        return map(bill);
    }

    @Override
    @Transactional(readOnly = true)
    public BillResponse getById(Long id) {
        return map(billRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("BILL_NOT_FOUND", "Bill with id " + id + " not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BillResponse> getByPatient(Long patientId) {
        return billRepository.findByPatientIdOrderByCreatedAtDesc(patientId).stream().map(this::map).toList();
    }

    private void recalculate(Bill bill) {
        BigDecimal subtotal = bill.getItems().stream().map(BillItem::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        bill.setSubtotal(subtotal);
        bill.setTotalAmount(subtotal.subtract(bill.getDiscount()).add(bill.getTax()));
    }

    private BillResponse map(Bill bill) {
        BigDecimal outstanding = bill.getTotalAmount().subtract(bill.getPaidAmount());
        return new BillResponse(bill.getId(), bill.getPatient().getId(), bill.getPatient().getFirstName() + " " + bill.getPatient().getLastName(), bill.getBillNumber(), bill.getStatus(), bill.getSubtotal(), bill.getDiscount(), bill.getTax(), bill.getTotalAmount(), bill.getPaidAmount(), outstanding, bill.getIssuedAt(), bill.getItems().stream().map(i -> new BillItemResponse(i.getId(), i.getItemType(), i.getDescription(), i.getQuantity(), i.getUnitPrice(), i.getAmount())).toList());
    }
}
