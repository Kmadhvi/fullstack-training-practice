package com.hms.service;

import com.hms.dto.request.NursingNoteRequest;
import com.hms.dto.response.NursingNoteResponse;

import java.util.List;

public interface NursingService {
    NursingNoteResponse addNote(NursingNoteRequest request);

    List<NursingNoteResponse> getByAdmission(Long admissionId);
}
