package com.playmatch.playmatch.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.playmatch.playmatch.dto.auth.LoginRequest;
import com.playmatch.playmatch.dto.auth.LoginResponse;
import com.playmatch.playmatch.dto.auth.RegisterRequest;
import com.playmatch.playmatch.entity.User;

import com.playmatch.playmatch.repository.UserRepository;
import com.playmatch.playmatch.security.CustomUserDetails;
import com.playmatch.playmatch.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void register(RegisterRequest request) {
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
        userRepository.save(user);

    }

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);
        return new LoginResponse(token);
    }
}
