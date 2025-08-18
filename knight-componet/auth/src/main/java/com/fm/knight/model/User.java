package com.fm.knight.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "TPL_USER_T")
@DynamicUpdate
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(length = 20, nullable = false)
    private String userName;
    @Column(length = 20, nullable = false)
    private String fullName;
    @Column(length = 1, nullable = false)
    private long gender;
    @Column
    private String avatar;
    @Column
    private String tel;
    @Column
    private String email;
    @Column(length = 1, nullable = false)
    private long state;
    @Lob
    @Column(nullable = false)
    private byte[] pwd;
    @Lob
    @Column(nullable = false)
    private byte[] salt;
    @Column(length = 200)
    private String comments;
    @Column
    private String createUser;
    @Column(nullable = false)
    private Date createDate;
    @Column(columnDefinition = "date")
    private Date lastUpdatePwdDate;

    public boolean checkPassword(String password) {
        return Arrays.equals(hashPassword(salt, password), pwd);
    }

    // 输入密码之后，经过该方法对密码进行加密，然后通过checkPassword方法，进行比较
    private static byte[] hashPassword(byte[] salt, String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            return md.digest(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }
    // 系统初始化时，调用该方法，为密码赋值
    public void resetPassword(String newPassword) {
        salt = generateSalt();
        pwd = hashPassword(salt, newPassword);
    }
    private static byte[] generateSalt() {
        try {
           var sr = SecureRandom.getInstance("SHA1PRNG");
           byte[] salt = new byte[64];
           sr.nextBytes(salt);
           return salt;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }

}
