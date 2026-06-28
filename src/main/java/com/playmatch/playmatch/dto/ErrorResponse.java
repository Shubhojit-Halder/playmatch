package com.playmatch.playmatch.dto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO for ErrorResponse Handling.
 * We're using a record here to automatically generate the constructor, getters, equals, hashCode, and toString methods.
 */
public record ErrorResponse(

        int status,

        String message,

        LocalDateTime timestamp,

        Map<String, String> errors

) {
}
