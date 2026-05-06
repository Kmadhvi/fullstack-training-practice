package com.hms.service;

import com.hms.dto.request.LoginRequest;
import com.hms.dto.request.RegisterUserRequest;
import com.hms.dto.response.AuthResponse;
import com.hms.dto.response.UserResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);

    UserResponse register(RegisterUserRequest request);
}
