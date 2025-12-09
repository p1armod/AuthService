package org.auth.Controller;


import lombok.RequiredArgsConstructor;
import org.auth.DTO.RefreshToken.RefreshTokenRequestDTO;
import org.auth.DTO.RefreshToken.RefreshTokenResponseDTO;
import org.auth.DTO.SignIn.SignInRequestDTO;
import org.auth.DTO.SignIn.SignInResponseDTO;
import org.auth.Entity.Token;
import org.auth.Service.JWTService;
import org.auth.Service.RefreshTokenService;
import org.auth.Service.UserDetailsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final RefreshTokenService tokenService;
    private final UserDetailsServiceImpl userDetailsService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth/v1/login")
    public ResponseEntity<SignInResponseDTO> signIn(@RequestBody SignInRequestDTO signInRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequestDTO.getUsername(), signInRequestDTO.getPassword()));
            if (authentication.isAuthenticated()) {
                RefreshTokenResponseDTO refreshTokenResponseDTO = tokenService.createRefreshToken(signInRequestDTO.getUsername());
                if(refreshTokenResponseDTO != null) {
                    return ResponseEntity.ok(SignInResponseDTO.builder()
                            .accessToken(refreshTokenResponseDTO.getAccessToken())
                            .refreshToken(refreshTokenResponseDTO.getRefreshToken())
                            .username(signInRequestDTO.getUsername()).build());
                }
            }
        } catch (Exception e) {
            System.out.println("Bad credentials");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @PostMapping("/auth/v1/refreshtoken")
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return ResponseEntity.ok(tokenService.checkValidity(refreshTokenRequestDTO));
    }
}
