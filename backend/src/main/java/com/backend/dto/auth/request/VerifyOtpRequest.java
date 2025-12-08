package com.backend.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VerifyOtpRequest(@NotBlank String otp, @Email(message = "invalid email") String email) {
}
