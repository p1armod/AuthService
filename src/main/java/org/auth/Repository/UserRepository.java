package org.auth.Repository;

import org.auth.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfo,Long> {


    UserInfo findByUserName(String userName);
}
