package org.auth.Service;

import org.springframework.security.core.userdetails.UserDetails;

public class JWTService {
    public String extractUsername(String token) {
        return null;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return true;
    }
}
