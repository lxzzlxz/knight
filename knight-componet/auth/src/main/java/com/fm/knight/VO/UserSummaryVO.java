package com.fm.knight.VO;

import com.fm.knight.model.User;

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
        this.username = user.getUserName();
        this.name = user.getFullName();
        this.gender = user.getGender();
        this.avatar = user.getAvatar();
        this.state = user.getState();
    }
}
