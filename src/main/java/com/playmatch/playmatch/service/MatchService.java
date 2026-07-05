package com.playmatch.playmatch.service;
import org.springframework.stereotype.Service;
import java.util.List;

import com.playmatch.playmatch.dto.match.CreateMatchRequest;
import com.playmatch.playmatch.dto.match.MatchResponse;
import com.playmatch.playmatch.entity.Match;
import com.playmatch.playmatch.exception.MatchNotFoundException;
import com.playmatch.playmatch.repository.MatchRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;

    public MatchResponse findMatchById(String id) {
        log.info("Fetching match with ID: {}", id);
        return matchRepository.findById(id)
                .map(match -> new MatchResponse(
                        match.getId(),
                        match.getTitle(),
                        match.getSport(),
                        match.getMatchTime().toString(),
                        match.getMaxPlayers()
                ))
                .orElseThrow(() -> new MatchNotFoundException(id));
    }

    public MatchResponse createMatch(CreateMatchRequest request) {
        // Implement the logic to create a match
        // For now, return a placeholder response
        if(matchRepository.existsByTitleAndSport(request.title(), request.sport())) {
            log.error("Match with title '{}' and sport '{}' already exists", request.title(), request.sport());
            throw new IllegalArgumentException("Match with the same title and sport already exists");
        }
        log.info("Creating match with title: {}", request.title());
        Match matchEntity = new Match();
        matchEntity.setTitle(request.title());
        matchEntity.setSport(request.sport());
        matchEntity.setMatchTime(request.matchTime());
        matchEntity.setMaxPlayers(request.maxPlayers());
        Match savedMatch = matchRepository.save(matchEntity);

        log.info("Match created with ID: {}", savedMatch.getId());
        return new MatchResponse(
                savedMatch.getId(),
                savedMatch.getTitle(),
                savedMatch.getSport(),
                savedMatch.getMatchTime().toString(),
                savedMatch.getMaxPlayers()
        );
    }

    public List<MatchResponse> getAllMatches() {
        log.info("Fetching all matches");
        List<MatchResponse>allMatches= matchRepository.findAll().stream()
                .map(match -> new MatchResponse(
                        match.getId(),
                        match.getTitle(),
                        match.getSport(),
                        match.getMatchTime().toString(),
                        match.getMaxPlayers()
                )).toList();
        if (allMatches.isEmpty()) {
            log.warn("No matches found in the database");
            throw new MatchNotFoundException("No matches found");
        }
        log.info("Returning list of all matches, count: {}", allMatches.size());
        return allMatches;
    }

    public MatchResponse updateMatch(String id, CreateMatchRequest request) {
        log.info("Updating match with ID: {}", id);
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException(id));

        match.setTitle(request.title());
        match.setSport(request.sport());
        match.setMatchTime(request.matchTime());
        match.setMaxPlayers(request.maxPlayers());

        Match updatedMatch = matchRepository.save(match);
        log.info("Match updated with ID: {}", updatedMatch.getId());
        return new MatchResponse(
                updatedMatch.getId(),
                updatedMatch.getTitle(),
                updatedMatch.getSport(),
                updatedMatch.getMatchTime().toString(),
                updatedMatch.getMaxPlayers()
        );
    }

    public void deleteMatch(String id) {
        log.info("Deleting match with ID: {}", id);
        if (!matchRepository.existsById(id)) {
            log.error("Match with ID {} not found for deletion", id);
            throw new MatchNotFoundException(id);
        }
        matchRepository.deleteById(id);
        log.info("Match with ID {} deleted successfully", id);
    }


}
