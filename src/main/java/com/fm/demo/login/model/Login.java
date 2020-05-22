package com.fm.demo.login.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity(name = "T_SYS_LOGIN")
@Data
public class Login {
    @Id
    private long id;
    @Column(name = "user_name", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "create_user")
    private String createUser;
    @Column(name = "create_date")
    private Date createDate;
}