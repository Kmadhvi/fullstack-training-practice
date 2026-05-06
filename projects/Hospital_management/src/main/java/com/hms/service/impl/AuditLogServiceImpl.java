package com.hms.service.impl;

import com.hms.dto.response.AuditLogResponse;
import com.hms.entity.AuditLog;
import com.hms.repository.AuditLogRepository;
import com.hms.service.AuditLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLogResponse> getAll() {
        return auditLogRepository.findAll().stream().map(this::map).toList();
    }

    private AuditLogResponse map(AuditLog log) {
        return new AuditLogResponse(log.getId(), log.getUser() == null ? null : log.getUser().getId(), log.getUser() == null ? null : log.getUser().getEmail(), log.getAction(), log.getEntityName(), log.getEntityId(), log.getDetails(), log.getIpAddress(), log.getCreatedAt());
    }
}
