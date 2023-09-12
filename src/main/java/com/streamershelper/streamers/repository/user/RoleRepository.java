package com.streamershelper.streamers.repository.user;

import com.streamershelper.streamers.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long>
{
    Role findByName(String name);
}
