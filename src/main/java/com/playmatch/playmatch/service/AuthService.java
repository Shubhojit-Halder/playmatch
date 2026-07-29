package com.playmatch.playmatch.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.playmatch.playmatch.dto.auth.LoginRequest;
import com.playmatch.playmatch.dto.auth.LoginResponse;
import com.playmatch.playmatch.dto.auth.RefreshTokenRequest;
import com.playmatch.playmatch.dto.auth.RegisterRequest;
import com.playmatch.playmatch.entity.RefreshToken;
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
    private final RefreshTokenService refreshTokenService;
    // private final User user;

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
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(userDetails.getUsername(),userDetails.getRole());
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(userDetails.getUser());
        return new LoginResponse(accessToken,newRefreshToken.getToken());
    }

    public LoginResponse refreshToken(RefreshTokenRequest request){
        RefreshToken oldToken = refreshTokenService.validateRefreshToken(request.refreshToken());
        User user = oldToken.getUser();

        String accessToken= jwtService.generateAccessToken(user.getEmail(),user.getRole());
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);

        return new LoginResponse(accessToken, newRefreshToken.getToken());

    }
}
