package com.playmatch.playmatch.dto.match;

import jakarta.validation.constraints.NotBlank;

public record UpdateMatchRequest(
    @NotBlank(message = "Title is mandatory")
    String title,
    @NotBlank(message = "Sport is mandatory")
    String sport,
    @NotBlank(message = "Match time is mandatory")
    String matchTime,
    @NotBlank(message = "Maximum players is mandatory")
    int maxPlayers
) {

}
