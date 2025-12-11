package com.backend.controller;

import com.backend.dto.Response;
import com.backend.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    @GetMapping("/info")
    public ResponseEntity<Response<?>> getUserInfo(){
        return ResponseEntity.ok(new Response<>(1000, "success", userService.getUserInfo()));
    }
    @PostMapping("/addAddress")
    public ResponseEntity<Response<?>> addAddress(){
        return ResponseEntity.ok(new Response<>(1000, "success", null));
    }
    @DeleteMapping("/deleteAddress")
    public ResponseEntity<Response<?>> deleteAddress(){
        return ResponseEntity.ok(new Response<>(1000, "success", null));
    }
    @PatchMapping("/update")
    public ResponseEntity<Response<?>> updateUserInfo(){
        return ResponseEntity.ok(new Response<>(1000, "success", null));
    }
}
