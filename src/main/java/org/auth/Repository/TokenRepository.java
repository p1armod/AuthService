package org.auth.Repository;

import org.auth.Entity.Token;
import org.auth.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Long> {
    Token findByUser(User user);

    Token findByToken(String refreshToken);
}
