package org.auth.DTO.SignIn;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import java.time.Instant;

@Builder
@Data
public class SignInResponseDTO {
    private String username;
    private String refreshToken;
    private String accessToken;
}
