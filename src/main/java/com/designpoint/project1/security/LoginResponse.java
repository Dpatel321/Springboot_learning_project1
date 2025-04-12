package com.designpoint.project1.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class LoginResponse {
    private String token;

    private long expiresIn;
}
