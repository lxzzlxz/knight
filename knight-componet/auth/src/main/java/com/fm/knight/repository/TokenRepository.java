package com.fm.knight.repository;

import com.fm.knight.model.Token;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {

}
