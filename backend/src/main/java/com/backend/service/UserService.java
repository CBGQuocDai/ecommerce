package com.backend.service;

import com.backend.dto.request.auth.LoginRequest;
import com.backend.dto.request.auth.RegisterRequest;
import com.backend.dto.request.auth.VerifyOtpRequest;
import com.backend.dto.response.auth.TokenResponse;
import com.backend.dto.request.user.AddAddressRequest;
import com.backend.dto.response.user.UserResponse;
import com.backend.entity.Address;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    TokenResponse login(LoginRequest req);
    void register(RegisterRequest req);
    TokenResponse verifyOtp(VerifyOtpRequest req);
    void logout(String token);
    UserResponse getUserInfo();
    List<Address> getAddress();
    List<Address> addAddress(AddAddressRequest req);

}
