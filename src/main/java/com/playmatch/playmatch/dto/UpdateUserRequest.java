package com.playmatch.playmatch.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
    @NotBlank(message = "Name is mandatory")
    String name,

    @NotBlank(message = "Email is mandatory")
    @Email
    String email
) {}
    
