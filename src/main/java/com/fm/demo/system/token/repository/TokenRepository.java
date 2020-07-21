package com.fm.demo.system.token.repository;

import com.fm.demo.system.token.model.Token;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {
    
}
