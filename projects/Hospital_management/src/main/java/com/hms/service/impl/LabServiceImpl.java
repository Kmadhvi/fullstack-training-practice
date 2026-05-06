package com.hms.service.impl;

import com.hms.dto.request.LabOrderRequest;
import com.hms.dto.request.LabResultRequest;
import com.hms.dto.response.LabOrderItemResponse;
import com.hms.dto.response.LabOrderResponse;
import com.hms.entity.Consultation;
import com.hms.entity.Doctor;
import com.hms.entity.LabOrder;
import com.hms.entity.LabOrderItem;
import com.hms.entity.Patient;
import com.hms.enums.LabOrderStatus;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.ConsultationRepository;
import com.hms.repository.DoctorRepository;
import com.hms.repository.LabOrderItemRepository;
import com.hms.repository.LabOrderRepository;
import com.hms.repository.PatientRepository;
import com.hms.service.AuditService;
import com.hms.service.LabService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LabServiceImpl implements LabService {

    private final LabOrderRepository labOrderRepository;
    private final LabOrderItemRepository labOrderItemRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ConsultationRepository consultationRepository;
    private final AuditService auditService;

    public LabServiceImpl(LabOrderRepository labOrderRepository, LabOrderItemRepository labOrderItemRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, ConsultationRepository consultationRepository, AuditService auditService) {
        this.labOrderRepository = labOrderRepository;
        this.labOrderItemRepository = labOrderItemRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.consultationRepository = consultationRepository;
        this.auditService = auditService;
    }

    @Override
    @Transactional
    public LabOrderResponse createOrder(LabOrderRequest request) {
        Patient patient = patientRepository.findById(request.patientId()).orElseThrow(() -> new ResourceNotFoundException("PATIENT_NOT_FOUND", "Patient with id " + request.patientId() + " not found"));
        Doctor doctor = doctorRepository.findById(request.doctorId()).orElseThrow(() -> new ResourceNotFoundException("DOCTOR_NOT_FOUND", "Doctor with id " + request.doctorId() + " not found"));
        Consultation consultation = request.consultationId() == null ? null : consultationRepository.findById(request.consultationId()).orElseThrow(() -> new ResourceNotFoundException("CONSULTATION_NOT_FOUND", "Consultation with id " + request.consultationId() + " not found"));
        LabOrder order = new LabOrder();
        order.setPatient(patient);
        order.setDoctor(doctor);
        order.setConsultation(consultation);
        order.setOrderedAt(LocalDateTime.now());
        request.items().forEach(itemRequest -> {
            LabOrderItem item = new LabOrderItem();
            item.setLabOrder(order);
            item.setTestName(itemRequest.testName());
            item.setSampleType(itemRequest.sampleType());
            order.getItems().add(item);
        });
        LabOrder saved = labOrderRepository.save(order);
        auditService.log("LAB_ORDER_CREATED", "LabOrder", saved.getId(), "Created lab order");
        return map(saved);
    }

    @Override
    @Transactional
    public LabOrderResponse updateItemResult(Long itemId, LabResultRequest request) {
        LabOrderItem item = labOrderItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("LAB_ORDER_ITEM_NOT_FOUND", "Lab order item with id " + itemId + " not found"));
        item.setStatus(request.status());
        item.setResultValue(request.resultValue());
        item.setReferenceRange(request.referenceRange());
        item.setRemarks(request.remarks());
        LabOrder order = item.getLabOrder();
        if (order.getItems().stream().allMatch(i -> i.getStatus() == LabOrderStatus.COMPLETED)) {
            order.setStatus(LabOrderStatus.COMPLETED);
            order.setCompletedAt(LocalDateTime.now());
        } else if (request.status() == LabOrderStatus.SAMPLE_COLLECTED) {
            order.setStatus(LabOrderStatus.SAMPLE_COLLECTED);
        } else if (request.status() == LabOrderStatus.IN_PROGRESS || request.status() == LabOrderStatus.COMPLETED) {
            order.setStatus(LabOrderStatus.IN_PROGRESS);
        }
        auditService.log("LAB_RESULT_UPDATED", "LabOrderItem", item.getId(), "Updated lab result");
        return map(order);
    }

    @Override
    @Transactional(readOnly = true)
    public LabOrderResponse getOrder(Long id) {
        return map(findOrder(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LabOrderResponse> getPatientOrders(Long patientId) {
        return labOrderRepository.findByPatientIdOrderByOrderedAtDesc(patientId).stream().map(this::map).toList();
    }

    private LabOrder findOrder(Long id) {
        return labOrderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("LAB_ORDER_NOT_FOUND", "Lab order with id " + id + " not found"));
    }

    private LabOrderResponse map(LabOrder order) {
        return new LabOrderResponse(order.getId(), order.getPatient().getId(), order.getPatient().getFirstName() + " " + order.getPatient().getLastName(), order.getDoctor().getId(), order.getDoctor().getUser().getFullName(), order.getConsultation() == null ? null : order.getConsultation().getId(), order.getStatus(), order.getOrderedAt(), order.getCompletedAt(), order.getItems().stream().map(i -> new LabOrderItemResponse(i.getId(), i.getTestName(), i.getSampleType(), i.getStatus(), i.getResultValue(), i.getReferenceRange(), i.getRemarks())).toList());
    }
}
