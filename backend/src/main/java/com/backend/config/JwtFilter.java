package com.backend.config;

import com.backend.entity.User;
import com.backend.service.impl.UserServiceImpl;
import com.backend.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserServiceImpl userService;
    private final RedisTemplate<String,Object> redisTemplate;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(token!= null) {
            try {
                token = token.replace("Bearer ", "");
                Claims claims = jwtUtils.extractClaims(token);
                String jit = (String) redisTemplate.opsForValue().get("token_"+claims.getSubject());
                if(jit==null|| !jit.equals(claims.getId())) {
                    User u = (User) userService.loadUserByUsername(claims.getSubject());
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(u, null, u.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                throw new RuntimeException("Invalid token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
