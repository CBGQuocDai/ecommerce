package com.backend.mapper;

import com.backend.dto.response.user.UserResponse;
import com.backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {
    UserResponse fromUserToUserResponse(User user);
}
