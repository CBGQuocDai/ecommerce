package com.backend.service;

import com.backend.dto.auth.request.LoginRequest;
import com.backend.dto.auth.request.RegisterRequest;
import com.backend.dto.auth.request.VerifyOtpRequest;
import com.backend.dto.auth.response.TokenResponse;
import com.backend.dto.user.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    TokenResponse login(LoginRequest req);
    void register(RegisterRequest req);
    TokenResponse verifyOtp(VerifyOtpRequest req);
    void logout(String token);
    UserResponse getUserInfo();
}
