package com.playmatch.playmatch.dto;

/**
 * DTO for representing a user response. We're using a record here to
 * automatically generate the constructor, getters, equals, hashCode, and
 * toString methods.
 */
public record UserResponse(
        String id,
        String name,
        String email,
        Integer rating
        ) {

}

