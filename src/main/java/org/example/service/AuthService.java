package org.example.service;

import org.example.dto.LoginRequest;
import org.example.dto.LoginResponse;
import org.example.dto.SignUpRequest;
import org.example.model.RefreshToken;
import org.example.model.User;
import org.example.repository.RefreshTokenRepository;
import org.example.repository.UserRepository;
import org.example.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokens;

    @Autowired
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RefreshTokenRepository refreshTokens){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokens = refreshTokens;
    }
    public RefreshToken mintRefreshToken(User user){
        RefreshToken rt = new RefreshToken();
        rt.setUser(user);
        rt.setToken(UUID.randomUUID().toString());
        rt.setExpiresAt(Instant.now().plus(30, ChronoUnit.DAYS));
        return refreshTokens.save(rt);
    }

    private RefreshToken rotate(RefreshToken old, User user){
        old.setRevoked(true);
        RefreshToken fresh = mintRefreshToken(user);
        old.setReplacedByToken(fresh.getToken());
        refreshTokens.save(old);
        return fresh;
    }
    public User registerUser(SignUpRequest request){
        //check for duplicates
        userRepository.findByEmail(request.email).ifPresent( u -> {
            throw new RuntimeException("Email already in use");
        });
        User user = new User();
        //user.setId(UUID.randomUUID());
        user.setName(request.name);
        user.setEmail(request.email);
        user.setPasswordHash(passwordEncoder.encode(request.password));
        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.email)
                .orElseThrow(() -> new RuntimeException("Invalid Credentials"));
        if(!passwordEncoder.matches(request.password, user.getPasswordHash()))
            throw new RuntimeException("Invalid Credentials");

        var claims = new HashMap<String, Object>();
        claims.put("userId", user.getId().toString());
        claims.put("name", user.getName());
        String access = JwtUtil.generateToken(user.getEmail(), claims);
        RefreshToken rt = mintRefreshToken(user);
        return new LoginResponse(access, rt.getToken());
    }

    public LoginResponse refresh(String presentedToken) {
        RefreshToken stored = refreshTokens.findByToken(presentedToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        if(stored.isRevoked() || stored.getExpiresAt().isBefore(Instant.now()))
            throw new RuntimeException("Refresh token expired or revoked");

        User user = stored.getUser();
        var claims = new HashMap<String, Object>();
        claims.put("userId", user.getId().toString());
        claims.put("name", user.getName());

        String access = JwtUtil.generateToken(user.getEmail(), claims);
        RefreshToken newRt = rotate(stored, user);
        return new LoginResponse(access, newRt.getToken());
    }

    public void logoutAll(User user){
        refreshTokens.deleteByUserId(user.getId());
    }
}
