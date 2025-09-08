package com.fm.knight.common.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fm.knight.dao.UserMapper;
import com.fm.knight.model.User;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserInitializer {
    private final UserMapper userRepo;

    public DefaultUserInitializer(UserMapper userRepo) {
        this.userRepo = userRepo;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (userRepo.selectList(new LambdaQueryWrapper<>()).isEmpty()) {
            User user = User.builder().userName("lzm").fullName("刘泽民").state(1).build();

            user.resetPassword("Lxzzlxz5@"); // 确保此方法正确处理 pwd 和 salt
            userRepo.insert(user);
        }
    }
}
