package com.playmatch.playmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playmatch.playmatch.entity.Match;
import com.playmatch.playmatch.enums.SportType;
public interface MatchRepository extends JpaRepository<Match, String> {
    boolean existsByTitleAndSport(String title,
            SportType sport);
}
