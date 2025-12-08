package com.backend.dto.auth.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
public record LoginRequest(
        @Email(message = "invalid email") String email,
        @NotBlank(message = "password is required") String password
) {

}
