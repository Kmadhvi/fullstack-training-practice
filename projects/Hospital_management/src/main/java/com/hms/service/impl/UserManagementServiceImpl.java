package com.hms.service.impl;

import com.hms.dto.request.RegisterUserRequest;
import com.hms.dto.response.UserResponse;
import com.hms.entity.User;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.UserRepository;
import com.hms.service.AuthService;
import com.hms.service.AuditService;
import com.hms.service.UserManagementService;
import com.hms.util.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final AuditService auditService;

    public UserManagementServiceImpl(UserRepository userRepository, AuthService authService, AuditService auditService) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.auditService = auditService;
    }

    @Override
    @Transactional
    public UserResponse create(RegisterUserRequest request) {
        UserResponse response = authService.register(request);
        auditService.log("USER_CREATED", "User", response.id(), "Created user " + response.email());
        return response;
    }

    @Override
    @Transactional
    public UserResponse setEnabled(Long id, boolean enabled) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND", "User with id " + id + " not found"));
        user.setEnabled(enabled);
        auditService.log("USER_STATUS_UPDATED", "User", user.getId(), "User enabled=" + enabled);
        return UserMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(UserMapper::toResponse).toList();
    }
}
