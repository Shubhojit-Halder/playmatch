package com.playmatch.playmatch.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playmatch.playmatch.dto.match.CreateMatchRequest;
import com.playmatch.playmatch.dto.match.MatchResponse;
import com.playmatch.playmatch.enums.SportType;
import com.playmatch.playmatch.service.MatchService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
@Slf4j
@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;
    @GetMapping("/{id}")
    public MatchResponse findMatchById(@PathVariable String id) {
        return matchService.findMatchById(id);
    }

    @PostMapping
    public MatchResponse createMatch(@Valid @RequestBody CreateMatchRequest request) {
        // Implement the logic to create a match
        log.info("Received request to create match with title: {}", request.title());
        return matchService.createMatch(request); // Placeholder return statement
          // Placeholder return statement
    }

    @GetMapping
    public List<MatchResponse> getAllMatches(@RequestParam(required = false) SportType sport, @RequestParam(required = false) String title , @RequestParam(required = false) LocalDateTime from, @RequestParam(required = false) LocalDateTime to) {
        // Implement the logic to retrieve all matches
        log.info("Received request to get all matches from MatchController");
        return matchService.getAllMatches(sport, title, from, to); // Placeholder return statement
    }
    
    @PutMapping("/{id}")
    public MatchResponse updateMatch(@PathVariable String id, @Valid @RequestBody CreateMatchRequest request) {
        // Implement the logic to update a match
        log.info("Received request to update match with ID, from MatchControllr: {}", id);
        return matchService.updateMatch(id, request); // Placeholder return statement
    }

    @DeleteMapping("/{id}")
    public void deleteMatch(@PathVariable String id) {
        // Implement the logic to delete a match
        log.info("Received request to delete match with ID, from MatchControllr: {}", id);
        matchService.deleteMatch(id); // Placeholder return statement
    }
}
