package com.fm.demo.system.token.VO;

import java.util.Date;
import java.util.List;

import com.fm.demo.system.token.model.Token;

import lombok.Data;

@Data
public class TokenVO {
    private String id;
    private UserSummaryVO user;
    private Date expireDate;
    private List<String> acl;
    private Long isEffective;  //1有效 2无效

    public TokenVO(Token token, List<String> acl) {
        this.id = token.getId();
        this.user = new UserSummaryVO(token.getUser());
        this.expireDate = token.getExpireDate();
        this.acl = acl;
    }
}