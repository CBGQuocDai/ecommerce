package com.backend.controller;

import com.backend.dto.Response;
import com.backend.dto.request.auth.LoginRequest;
import com.backend.dto.request.auth.RegisterRequest;
import com.backend.dto.request.auth.VerifyOtpRequest;
import com.backend.dto.response.auth.TokenResponse;
import com.backend.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserServiceImpl userService;
    @PostMapping("/login")
    public ResponseEntity<Response<TokenResponse>> login(@Valid @RequestBody LoginRequest req){
        return ResponseEntity.ok(new Response<>(1000,"success",userService.login(req)));
    }
    @PostMapping("/register")
    public ResponseEntity<Response<?>> register(@Valid @RequestBody RegisterRequest req){
        userService.register(req);
        return ResponseEntity.ok(new Response<>(1000, "success",null));
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<Response<TokenResponse>> verifyOtp(@Valid @RequestBody VerifyOtpRequest req){
        return ResponseEntity.ok(new Response<>(1000,"success",userService.verifyOtp(req)));
    }
    @PostMapping("/logout")
    public ResponseEntity<Response<?>> logout(@RequestHeader("Authorization") String token){
        userService.logout(token.substring(7));
        return ResponseEntity.ok(new Response<>(1000,"success",null));
    }
}
