package com.example.security.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Request payload for user login.
 */
@Data
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
