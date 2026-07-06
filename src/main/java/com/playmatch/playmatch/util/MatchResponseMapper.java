package com.playmatch.playmatch.util;
import com.playmatch.playmatch.dto.match.MatchResponse;
import com.playmatch.playmatch.entity.Match;
import org.springframework.stereotype.Component;
@Component
public class MatchResponseMapper {
    public MatchResponse toMatchResponse(Match match) {
        return new MatchResponse(
                match.getId(),
                match.getTitle(),
                match.getSport(),
                match.getMatchTime().toString(),
                match.getMaxPlayers(),
                match.getBookedPlayers()
        );
    }
}
