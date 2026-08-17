package com.playmatch.playmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.playmatch.playmatch.entity.Match;
import com.playmatch.playmatch.enums.SportType;
public interface MatchRepository extends JpaRepository<Match, String>,JpaSpecificationExecutor<Match> {
    boolean existsByTitleAndSport(String title,
            SportType sport);
}
