package com.playmatch.playmatch.dto.match;
import com.playmatch.playmatch.enums.SportType;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
/**
 * DTO for creating a new user.
 * We're using a record here to automatically generate the constructor, getters, equals, hashCode, and toString methods.
 */
public record CreateMatchRequest(
    @NotBlank(message = "Match name is mandatory")
    String title,
    @NotNull(message = "Sport is mandatory")
    SportType sport,
    @NotNull(message = "Match time is mandatory")
    LocalDateTime matchTime,
    
    @NotNull(message = "Maximum players is mandatory")
    @Min(value = 1, message = "Maximum players must be at least 1")
    int maxPlayers
) {
    
}
