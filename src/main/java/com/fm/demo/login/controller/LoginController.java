package com.fm.demo.login.controller;

import com.fm.demo.common.ApiBaseController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.Data;

@ApiBaseController
public class LoginController {
    @Data
    public static class User{
        private String username;
        private String password;
    }
    @PostMapping("/login")
    public boolean login(@RequestBody User user) {
        if(user != null) {
            return true;
        }
        return false;
    }
}