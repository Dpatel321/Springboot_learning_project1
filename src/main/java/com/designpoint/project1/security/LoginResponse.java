package com.designpoint.project1.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponse {
    private String token;

    private long expiresIn;
}
