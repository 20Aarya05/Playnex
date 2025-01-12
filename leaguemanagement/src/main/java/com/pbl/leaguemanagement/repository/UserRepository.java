package com.pbl.leaguemanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pbl.leaguemanagement.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
    User findByUsername(String username);
    Optional<User> findByNumber(String number);
}

