package com.playmatch.playmatch.dto.match;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
/**
 * DTO for creating a new user.
 * We're using a record here to automatically generate the constructor, getters, equals, hashCode, and toString methods.
 */
public record CreateMatchRequest(
    @NotBlank(message = "Match name is mandatory")
    String title,
    @NotBlank(message = "Sport is mandatory")
    String sport,
    @NotBlank(message = "Match time is mandatory")
    LocalDateTime matchTime,
    
    @NotBlank(message = "Maximum players is mandatory")
    int maxPlayers
) {
    
}
