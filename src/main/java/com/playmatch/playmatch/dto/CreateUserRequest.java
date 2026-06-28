package com.playmatch.playmatch.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for creating a new user.
 * We're using a record here to automatically generate the constructor, getters, equals, hashCode, and toString methods.
 */
public record CreateUserRequest(
        @NotBlank(message = "Name is mandatory")
        String name,
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        String email
        ) {

}
