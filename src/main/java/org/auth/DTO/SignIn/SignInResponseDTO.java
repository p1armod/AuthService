package org.auth.DTO.SignIn;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import java.time.Instant;

public class SignInResponseDTO {
    private String refreshToken;
    private String accessToken;
    private String tokenType;
    private Instant expiresIn;
}
