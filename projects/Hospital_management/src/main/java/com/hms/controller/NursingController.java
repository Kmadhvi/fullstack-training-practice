package com.hms.controller;

import com.hms.dto.request.NursingNoteRequest;
import com.hms.dto.response.ApiResponse;
import com.hms.dto.response.NursingNoteResponse;
import com.hms.service.NursingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nursing")
public class NursingController {
    private final NursingService nursingService;

    public NursingController(NursingService nursingService) {
        this.nursingService = nursingService;
    }

    @PostMapping("/notes")
    @PreAuthorize("hasAnyRole('ADMIN','NURSE')")
    public ResponseEntity<ApiResponse<NursingNoteResponse>> addNote(@Valid @RequestBody NursingNoteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(nursingService.addNote(request), "Nursing note added successfully"));
    }

    @GetMapping("/admission/{admissionId}/notes")
    @PreAuthorize("hasAnyRole('ADMIN','NURSE','DOCTOR')")
    public ResponseEntity<ApiResponse<List<NursingNoteResponse>>> getByAdmission(@PathVariable Long admissionId) {
        return ResponseEntity.ok(ApiResponse.success(nursingService.getByAdmission(admissionId)));
    }
}
