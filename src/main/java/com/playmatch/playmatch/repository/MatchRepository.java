package com.playmatch.playmatch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playmatch.playmatch.entity.Match;

import jakarta.validation.constraints.NotBlank;


public interface MatchRepository extends JpaRepository<Match, String> {
    Optional<Match> findById(String id);

    boolean existsByTitleAndSport(String title,
            String sport);
}
