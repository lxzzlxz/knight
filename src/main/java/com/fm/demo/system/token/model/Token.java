package com.fm.demo.system.token.model;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fm.demo.system.user.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_SYS_TOKEN")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Token {
    @Id
    @Column(length = 100, nullable = false)
    private String id = generateTokenId();
    @ManyToOne()
    @JoinColumn(name = "USERNAME", nullable = false)
    private User user;
    @Column(length = 30, nullable = false)
    private String ip;
    @Column(length = 30, nullable = false)
    private String clientType;
    @Column(columnDefinition = "date", nullable = false)
    private Date createDate;
    @Column(columnDefinition = "date", nullable = false)
    private Date expireDate;

    private static String generateTokenId() {
        try {
            var sr = SecureRandom.getInstance("SHA1PRNG");
            var rawId = new byte[64];
            sr.nextBytes(rawId);
            return Base64.getUrlEncoder().encodeToString(rawId);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }
}
