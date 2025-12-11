package com.backend.dto.user.response;

public record UserResponse(long id, String fullName, String email, String phoneNumber, String avatarPath) {
}
