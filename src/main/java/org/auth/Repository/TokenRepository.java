package org.auth.Repository;

import org.auth.Entity.Token;
import org.auth.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Long> {
    Token findByUser(UserInfo user);

    Token findByToken(String refreshToken);
}
