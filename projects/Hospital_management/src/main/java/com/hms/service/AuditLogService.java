package com.hms.service;

import com.hms.dto.response.AuditLogResponse;

import java.util.List;

public interface AuditLogService {
    List<AuditLogResponse> getAll();
}
