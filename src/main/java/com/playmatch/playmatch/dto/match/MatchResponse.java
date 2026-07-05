package com.playmatch.playmatch.dto.match;

public record MatchResponse(
    String id,
    String title,
    String sport,
    String matchTime,
    int maxPlayers
) {

}
