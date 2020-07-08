package com.fm.demo.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultEnum {
    INCORRECT_USERNAME_OR_PASSWORD(5, "用户名或密码错误");

    private long code;
    private String msg;
}
