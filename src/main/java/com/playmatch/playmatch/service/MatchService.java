package com.playmatch.playmatch.service;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.playmatch.playmatch.dto.match.CreateMatchRequest;
import com.playmatch.playmatch.dto.match.MatchResponse;
import com.playmatch.playmatch.entity.Match;
import com.playmatch.playmatch.entity.User;
import com.playmatch.playmatch.enums.SportType;
import com.playmatch.playmatch.exception.MatchNotFoundException;
import com.playmatch.playmatch.exception.UnauthorizedException;
import com.playmatch.playmatch.exception.UserNotFoundException;
import com.playmatch.playmatch.repository.MatchRepository;
import com.playmatch.playmatch.repository.UserRepository;
import com.playmatch.playmatch.security.CustomUserDetails;
import com.playmatch.playmatch.specification.MatchSpecification;
import com.playmatch.playmatch.util.MatchResponseMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    public MatchResponse findMatchById(String id) {
        log.info("Fetching match with ID: {}", id);
        return matchRepository.findById(id)
                .map(match -> new MatchResponseMapper().toMatchResponse(match))
                .orElseThrow(() -> new MatchNotFoundException(id));
    }
    
    // public String getCurrentUserId(){
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     log.info("Current user: {}", authentication.getPrincipal());
    //     String userEmail = authentication.getPrincipal().toString();
    //     return userEmail;
    // }

    public User getCurrentUser(){
         Authentication auth= SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails)auth.getPrincipal();
        
        return userDetails.getUser();
    }

    public MatchResponse createMatch(CreateMatchRequest request) {
        // Implement the logic to create a match
        // For now, return a placeholder response
        if(matchRepository.existsByTitleAndSport(request.title(), request.sport())) {
            log.error("Match with title '{}' and sport '{}' already exists", request.title(), request.sport());
            throw new IllegalArgumentException("Match with the same title and sport already exists");
        }
        
        User currentUser = getCurrentUser();
        
        log.info("Creating match with title: {}", request.title()); 
 
        Match matchEntity = new Match();
   
        matchEntity.setTitle(request.title());
        matchEntity.setSport(request.sport());
        matchEntity.setMatchTime(request.matchTime());
        matchEntity.setMaxPlayers(request.maxPlayers());
        matchEntity.setCreatedBy(currentUser);
        Match savedMatch = matchRepository.save(matchEntity);

        log.info("Match created with ID: {}", savedMatch.getId());
        return new MatchResponseMapper().toMatchResponse(savedMatch);
    }

    public List<MatchResponse> getAllMatches(SportType sport, String title) {
        log.info("Fetching all matches");
        Specification<Match> specification =
        MatchSpecification.hasSport(sport).and(MatchSpecification.titleContains(title));

        List<MatchResponse>allMatches= matchRepository.findAll(specification).stream()
                .map(match -> new MatchResponseMapper().toMatchResponse(match))
                .toList();
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

        if(null!=match.getCreatedBy() && !match.getCreatedBy().getId().equalsIgnoreCase(getCurrentUser().getId()))
            throw new UnauthorizedException("You are not allowed to update this match.");

        match.setTitle(request.title());
        match.setSport(request.sport());
        match.setMatchTime(request.matchTime());
        match.setMaxPlayers(request.maxPlayers());
        // match.setUpdatedAt(java.time.LocalDateTime.now());

        Match updatedMatch = matchRepository.save(match);
        log.info("Match updated with ID: {}", updatedMatch.getId());
        return new MatchResponseMapper().toMatchResponse(updatedMatch);
    }

    public void deleteMatch(String id) {
        log.info("Deleting match with ID: {}", id);
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException(id));
        String userId = getCurrentUser().getId();
        if(null==match.getCreatedBy() || match.getCreatedBy().getId().equalsIgnoreCase(userId)) {
            log.info("User {} is authorized to delete match {}", getCurrentUser().getId(), id);
        } else {
            throw new UnauthorizedException("You are not allowed to delete this match.");
        }
        matchRepository.deleteById(id);
        log.info("Match with ID {} deleted successfully", id);
    }

}
