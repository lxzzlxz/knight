package com.fm.knight.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fm.knight.knight.model.BaseLamModel;
import lombok.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

@Data
@TableName("TPL_USER_T") // MyBatis-Plus 注解
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseLamModel {
    private String userName;
    private String fullName;
    private byte[] pwd;
    private byte[] salt;
    private String email;
    private String phone;
    private long state;

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

    public boolean checkPassword(String password) {
        return Arrays.equals(hashPassword(salt, password), pwd);
    }

    // 系统初始化时，调用该方法，为密码赋值
    public void resetPassword(String newPassword) {
        salt = generateSalt();
        pwd = hashPassword(salt, newPassword);
    }
}
