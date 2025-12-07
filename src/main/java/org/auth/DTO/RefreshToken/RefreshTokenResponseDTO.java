package org.auth.DTO.RefreshToken;

import lombok.Data;

import java.time.Instant;

@Data
public class RefreshTokenResponseDTO {
    private String refreshToken;
    private String accessToken;
}
