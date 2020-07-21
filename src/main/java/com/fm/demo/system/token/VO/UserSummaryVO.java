package com.fm.demo.system.token.VO;

import com.fm.demo.system.user.model.User;

import lombok.Getter;
import lombok.NonNull;
@Getter
public class UserSummaryVO {
    private String username;
    private String name;
    private long gender;
    private long training;
    private String avatar;
    private long state;

    public UserSummaryVO(@NonNull User user) {
        this.username = user.getUsername();
        this.name = user.getFullname();
        this.gender = user.getGender();
        this.avatar = user.getAvatar();
        this.state = user.getState();
    }
}
