package com.fm.knight.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;


import com.fm.knight.common.ApiBaseController;
import com.fm.knight.error.ForbiddenException;
import com.fm.knight.error.ResultEnum;
import com.fm.knight.VO.TokenVO;
import com.fm.knight.repository.TokenRepository;
import com.fm.knight.model.Token;
import com.fm.knight.model.User;
import com.fm.knight.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
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
        Optional<User> opUser = userRepo.findByUsername(body.getUsername());
        if (!opUser.isPresent()) {
            throw new ForbiddenException(ResultEnum.INCORRECT_USERNAME_OR_PASSWORD);
        }
        User user = opUser.get();
        if(user.getState() != 1 || !user.checkPassword(body.password)) {
            throw new ForbiddenException(ResultEnum.INCORRECT_USERNAME_OR_PASSWORD);
        }
        Date _now = new Date();
        Token token = Token.builder().user(user).ip(request.getRemoteAddr()).clientType(body.getClientType())
        .createDate(_now).expireDate(new Date(_now.getTime() + TOKEN_VALID_PERIOD))
        .build();
        token = tokenRepo.save(token);
        ArrayList<String> acl = new ArrayList<>();
        return new TokenVO(token, acl);
    }
}
