package com.fm.demo.login.controller;

import com.fm.demo.common.ApiBaseController;
import com.fm.demo.error.ForbiddenException;
import com.fm.demo.error.ResultEnum;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;

@ApiBaseController
public class LoginController {

    @Data
    public static class User{
        private String username;
        private String password;
    }
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public boolean login(@RequestBody User user) {
        if(!(user.password.equals("1"))) {
            return true;
        } else {
            throw new ForbiddenException(ResultEnum.INCORRECT_USERNAME_OR_PASSWORD);
        }
    }
}
