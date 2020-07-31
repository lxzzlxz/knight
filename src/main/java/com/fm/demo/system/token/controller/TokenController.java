package com.fm.demo.system.token.controller;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.fm.demo.common.ApiBaseController;
import com.fm.demo.error.ForbiddenException;
import com.fm.demo.error.ResultEnum;
import com.fm.demo.system.token.VO.TokenVO;
import com.fm.demo.system.token.model.Token;
import com.fm.demo.system.token.repository.TokenRepository;
import com.fm.demo.system.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;

@ApiBaseController
public class TokenController {
    static final long TOKEN_VALID_PERIOD = 24L * 60 * 60 * 1000; // 1天
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TokenRepository tokenRepo;
    @Data
    public static class LoginRequestBody{
        private String username;
        private String password;
        private String clientType;
    }
    @PostMapping("/tokens")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public TokenVO login(@RequestBody LoginRequestBody body, HttpServletRequest request) {
        var opUser = userRepo.findByUsername(body.getUsername());
        if (opUser.isEmpty()) {
            throw new ForbiddenException(ResultEnum.INCORRECT_USERNAME_OR_PASSWORD);
        }
        var user = opUser.get();
        if(user.getState() != 1 || !user.checkPassword(body.password)) {
            throw new ForbiddenException(ResultEnum.INCORRECT_USERNAME_OR_PASSWORD);
        }
        var _now = new Date();
        var token = Token.builder().user(user).ip(request.getRemoteAddr()).clientType(body.getClientType())
        .createDate(_now).expireDate(new Date(_now.getTime() + TOKEN_VALID_PERIOD))
        .build();
        token = tokenRepo.save(token);
        var acl = new ArrayList<String>();
        TokenVO tokenVO = new TokenVO(token, acl);
        return tokenVO;
    }
}
