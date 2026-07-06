package com.playmatch.playmatch.dto.booking;
import jakarta.validation.constraints.NotBlank;

public record BookingRequest(
    @NotBlank(message = "User ID is mandatory")
    String userId,
    @NotBlank(message = "Match ID is mandatory")
    String matchId

) {}
