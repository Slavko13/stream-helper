package com.streamershelper.streamers.repository.user;

import com.streamershelper.streamers.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{

    User findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
}
