package com.hms.service;

public interface AuditService {
    void log(String action, String entityName, Long entityId, String details);
}
