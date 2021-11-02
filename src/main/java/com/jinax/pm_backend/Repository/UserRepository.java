package com.jinax.pm_backend.Repository;

import com.jinax.pm_backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : chara
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> getUserByUsernameEquals(String username);

}
