package com.playmatch.playmatch.service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.playmatch.playmatch.entity.RefreshToken;
import com.playmatch.playmatch.entity.User;
import com.playmatch.playmatch.exception.RefreshTokenExpiredException;
import com.playmatch.playmatch.exception.RefreshTokenNotFoundException;
import com.playmatch.playmatch.repository.RefreshTokenRepository;
import com.playmatch.playmatch.repository.UserRepository;
import com.playmatch.playmatch.security.CustomUserDetails;
import com.playmatch.playmatch.security.JwtService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepo;
    private final JwtService jwtService;

    @Transactional
    public RefreshToken createRefreshToken(User user){
        deleteRefreshToken(user);
        String token = jwtService.generateRefreshToken(user.getEmail(),user.getRole());
        RefreshToken refreshToken = RefreshToken.builder()
        .user(user)
        .token(token)
        .expiryDate(Instant.now().plus(7, ChronoUnit.DAYS))
        .createdAt(Instant.now())
        .build();
        return refreshTokenRepo.save(refreshToken);
    }

    @Transactional
    public RefreshToken validateRefreshToken(String token){
        RefreshToken refreshToken = refreshTokenRepo.findByToken(token)
                                    .orElseThrow(()->new RefreshTokenNotFoundException("Invalid Refresh Token"));
        if(refreshToken.getExpiryDate().isBefore(Instant.now())){
            refreshTokenRepo.delete(refreshToken);
            throw new RefreshTokenExpiredException("Refresh token has expired!");
        }
        return refreshToken;
    }

    @Transactional
    public void deleteRefreshToken(User user){
        refreshTokenRepo.deleteByUser(user);
    }


}
