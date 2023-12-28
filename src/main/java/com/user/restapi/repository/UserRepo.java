package com.user.restapi.repository;

import com.user.restapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
       User findByEmail(String email);
       boolean existsByUsername(String username);
       boolean existsByEmail(String email);
}
