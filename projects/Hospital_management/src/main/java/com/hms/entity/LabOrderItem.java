package com.hms.entity;

import com.hms.enums.LabOrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "lab_order_items")
public class LabOrderItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_order_id", nullable = false)
    private LabOrder labOrder;

    @Column(name = "test_name", nullable = false, length = 180)
    private String testName;

    @Column(name = "sample_type", nullable = false, length = 80)
    private String sampleType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LabOrderStatus status = LabOrderStatus.ORDERED;

    @Column(name = "result_value")
    private String resultValue;

    @Column(name = "reference_range", length = 120)
    private String referenceRange;

    @Column(length = 500)
    private String remarks;
}
