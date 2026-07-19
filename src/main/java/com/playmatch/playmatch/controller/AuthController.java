package com.playmatch.playmatch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.playmatch.playmatch.service.AuthService;
import com.playmatch.playmatch.dto.auth.RegisterRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.playmatch.playmatch.dto.auth.LoginRequest;
import com.playmatch.playmatch.dto.auth.LoginResponse;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    public final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        //TODO: process POST request
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("User generated Successfully");  
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    
}
