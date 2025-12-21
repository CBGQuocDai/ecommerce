package com.backend.mapper.impl;

import com.backend.dto.response.user.UserResponse;
import com.backend.entity.User;
import com.backend.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserResponse fromUserToUserResponse(User user) {
        return new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getPhoneNumber(),user.getAvatarPath());
    }
}
