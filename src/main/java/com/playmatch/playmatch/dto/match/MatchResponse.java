package com.playmatch.playmatch.dto.match;
import com.playmatch.playmatch.enums.SportType;
public record MatchResponse(
    String id,
    String title,
    SportType sport,
    String matchTime,
    Integer maxPlayers,
    Integer currentPlayers
) {

}
