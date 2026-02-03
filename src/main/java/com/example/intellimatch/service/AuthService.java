package com.example.intellimatch.service;

import com.example.intellimatch.dto.auth.LoginRequest;
import com.example.intellimatch.dto.auth.LoginResponse;
import com.example.intellimatch.dto.auth.RegisterRequest;
import com.example.intellimatch.entity.RevokedToken;
import com.example.intellimatch.entity.User;
import com.example.intellimatch.enums.Role;
import com.example.intellimatch.repository.RevokedTokenRepository;
import com.example.intellimatch.repository.UserRepository;
import com.example.intellimatch.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RevokedTokenRepository revokedTokenRepository;

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = jwtTokenProvider.generateToken(request.getEmail());
        return new LoginResponse(token, request.getEmail());
    }

    @Transactional
    public LoginResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token = jwtTokenProvider.generateToken(user.getEmail());
        return new LoginResponse(token, user.getEmail());
    }

    public void logout(String token) {
        RevokedToken revokedToken = RevokedToken.builder()
                .token(token)
                .revokedAt(LocalDateTime.now())
                .build();
        revokedTokenRepository.save(revokedToken);
    }
}