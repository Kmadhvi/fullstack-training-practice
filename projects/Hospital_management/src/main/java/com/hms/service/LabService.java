package com.hms.service;

import com.hms.dto.request.LabOrderRequest;
import com.hms.dto.request.LabResultRequest;
import com.hms.dto.response.LabOrderResponse;

import java.util.List;

public interface LabService {
    LabOrderResponse createOrder(LabOrderRequest request);

    LabOrderResponse updateItemResult(Long itemId, LabResultRequest request);

    LabOrderResponse getOrder(Long id);

    List<LabOrderResponse> getPatientOrders(Long patientId);
}
