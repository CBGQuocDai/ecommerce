package com.backend.dto.response.user;


public record UserResponse(long id, String fullName, String email, String phoneNumber, String avatarPath) {
}
