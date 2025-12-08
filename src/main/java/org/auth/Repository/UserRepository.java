package org.auth.Repository;

import org.auth.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {


    User findByUserName(String userName);
}
