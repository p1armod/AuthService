package org.auth.Repository;

import org.auth.Entity.Role;
import org.auth.Entity.Type.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleName(RoleType roleType);
}
