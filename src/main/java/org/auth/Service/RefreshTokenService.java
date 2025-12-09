package org.auth.Service;

import lombok.RequiredArgsConstructor;
import org.auth.DTO.RefreshToken.RefreshTokenRequestDTO;
import org.auth.DTO.RefreshToken.RefreshTokenResponseDTO;
import org.auth.Entity.Token;
import org.auth.Entity.UserInfo;
import org.auth.Entity.UserInfo;
import org.auth.Repository.TokenRepository;
import org.auth.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final TokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final ModelMapper modelMapper;

    private static final long RefreshTokenValidity = 1000L*3600*24*30;

    public RefreshTokenResponseDTO createRefreshToken(String userName) {
        UserInfo user = userRepository.findByUserName(userName);
        Token token = Token.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(RefreshTokenValidity))
                .build();
        refreshTokenRepository.save(token);
        RefreshTokenResponseDTO refreshTokenResponseDTO = new RefreshTokenResponseDTO();
        UserDetails userDetails = modelMapper.map(user, UserDetails.class);
        refreshTokenResponseDTO.setAccessToken(jwtService.generateToken(userDetails));
        refreshTokenResponseDTO.setRefreshToken(token.getToken());
        return refreshTokenResponseDTO;
    }

    public RefreshTokenResponseDTO checkValidity(RefreshTokenRequestDTO refreshToken) {
        Token token = refreshTokenRepository.findByToken(refreshToken.getRefreshToken());
        if (token == null) {
            throw new RuntimeException("Invalid refresh token");
        }

        if(token.getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Token is expired, Please login again.");
        }
        RefreshTokenResponseDTO refreshTokenResponseDTO = new RefreshTokenResponseDTO();
        UserDetails userDetails = modelMapper.map(token.getUser(), UserDetails.class);
        refreshTokenResponseDTO.setAccessToken(jwtService.generateToken(userDetails));
        refreshTokenResponseDTO.setRefreshToken(token.getToken());
        return refreshTokenResponseDTO;
    }


}
