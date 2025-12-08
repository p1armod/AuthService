package org.auth.Controller;


import lombok.RequiredArgsConstructor;
import org.auth.DTO.SignUp.SignUpRequestDTO;
import org.auth.DTO.SignUp.SignUpResponseDTO;
import org.auth.Service.JWTService;
import org.auth.Service.UserDetailsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserDetailsServiceImpl userDetailsService;
    private final TokenService tokenService;
    private final JWTService jwtService;


    @PostMapping("/auth/v1/signup")
    public ResponseEntity<SignUpResponseDTO> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        try {
            return ResponseEntity.created(URI.create("/")).body(userDetailsService.signUp(signUpRequestDTO));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
