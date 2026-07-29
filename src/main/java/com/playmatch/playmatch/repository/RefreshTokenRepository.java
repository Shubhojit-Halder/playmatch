package com.playmatch.playmatch.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playmatch.playmatch.entity.User;
import com.playmatch.playmatch.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String>{
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUser(User user);
    // public void deleteUsclser(User user);

    public void deleteByUser(User user);
}
