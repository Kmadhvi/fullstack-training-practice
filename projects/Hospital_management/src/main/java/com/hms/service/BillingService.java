package com.hms.service;

import com.hms.dto.request.BillRequest;
import com.hms.dto.request.PaymentRequest;
import com.hms.dto.response.BillResponse;

import java.util.List;

public interface BillingService {
    BillResponse create(BillRequest request);

    BillResponse recordPayment(Long billId, PaymentRequest request);

    BillResponse getById(Long id);

    List<BillResponse> getByPatient(Long patientId);
}
