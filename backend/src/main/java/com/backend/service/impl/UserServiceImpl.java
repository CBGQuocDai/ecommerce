package com.backend.service.impl;

import com.backend.dto.auth.request.LoginRequest;
import com.backend.dto.auth.request.RegisterRequest;
import com.backend.dto.auth.request.VerifyOtpRequest;
import com.backend.dto.auth.response.TokenResponse;
import com.backend.entity.User;
import com.backend.repository.UserRepository;
import com.backend.service.UserService;
import com.backend.utils.JwtUtils;
import com.backend.utils.OtpUtils;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String,Object> redisTemplate;
    @Transactional
    @Override
    public TokenResponse login(LoginRequest req) {
        User u = userRepository.findUserByEmail(req.email());
        if(u==null) {
            throw new RuntimeException("User not found");
        }
        if(!passwordEncoder.matches(req.password(), u.getPassword())|| !u.isActive()) {
            log.info("login failed");
            throw new RuntimeException("email or password is incorrect");
        }
        return new TokenResponse(jwtUtils.generateToken(u));
    }
    @Transactional
    @Override
    public void register(RegisterRequest req) {
        User u= userRepository.findUserByEmail(req.email());
        log.info("register email: {}", req.email());
        // validate email
        if(u!=null&& u.isActive()) {
            throw new RuntimeException("email already exists");
        }
        if(u==null) u=new User();
        u.setFullName(req.fullName());
        u.setEmail(req.email());
        u.setPassword(passwordEncoder.encode(req.password()));
        u.setActive(false);
        u.setCreatedAt(new java.util.Date());
        u.setUpdatedAt(new java.util.Date());
        userRepository.save(u);
        String otp = OtpUtils.generateOtp();
        log.info("otp: {}", otp);
        redisTemplate.opsForValue().set(u.getEmail(), otp,2,TimeUnit.MINUTES);
        //send email
    }
    @Transactional
    @Override
    public TokenResponse verifyOtp(VerifyOtpRequest req) {
        String otp = (String) redisTemplate.opsForValue().get(req.email());
        if(otp!=null&& otp.equals(req.otp())) {
            User user = userRepository.findUserByEmail(req.email());
            user.setActive(true);
            userRepository.save(user);
            return new TokenResponse(jwtUtils.generateToken(userRepository.findUserByEmail(req.email())));
        } else {throw new RuntimeException("otp is incorrect");}
    }
    @Transactional
    @Override
    public void logout(String token) {
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Claims claims = jwtUtils.extractClaims(token);
        long ttl = claims.getExpiration().getTime() - System.currentTimeMillis();
        redisTemplate.opsForValue().set("token_"+u.getEmail(), claims.getId(),ttl, TimeUnit.MILLISECONDS);
    }
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findUserByEmail(username);
        if(user!=null) user.setPassword("");
        return user;
    }
}
