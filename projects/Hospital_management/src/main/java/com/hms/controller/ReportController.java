package com.hms.controller;

import com.hms.dto.response.ApiResponse;
import com.hms.dto.response.ReportSummaryResponse;
import com.hms.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/summary")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ReportSummaryResponse>> summary() {
        return ResponseEntity.ok(ApiResponse.success(reportService.summary()));
    }
}
