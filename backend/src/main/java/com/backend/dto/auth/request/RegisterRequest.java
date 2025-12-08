package com.backend.dto.auth.request;

import com.backend.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "fullName is required") String fullName,
        @Email(message = "invalid email") String email,
        @NotBlank(message = "password is required") @Size(min =6, message = "password is too short") String password,
        @NotNull Role role
        ) {
}
