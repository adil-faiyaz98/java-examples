package com.example.security.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Simple message response payload.
 */
@Data
@AllArgsConstructor
public class MessageResponse {
    private String message;
}
