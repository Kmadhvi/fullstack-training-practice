package com.hms.service;

import com.hms.dto.request.RegisterUserRequest;
import com.hms.dto.response.UserResponse;

import java.util.List;

public interface UserManagementService {
    UserResponse create(RegisterUserRequest request);

    UserResponse setEnabled(Long id, boolean enabled);

    List<UserResponse> getAll();
}
