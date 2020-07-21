package com.fm.demo.system.user.repository;

import java.util.Optional;

import com.fm.demo.system.user.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}
