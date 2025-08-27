package com.fm.knight.common.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fm.knight.model.User;
import com.fm.knight.dao.UserDao;
import lombok.var;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitializatingSystemInterceptor implements ApplicationListener<ContextRefreshedEvent>{
    private final UserDao userRepo;

    public InitializatingSystemInterceptor(UserDao userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if(userRepo.selectList(queryWrapper).size() == 0) {
            // 初始化用户
            var user = User.builder()
            .userName("lzm")
            .fullName("刘泽民")
            .state(1)
            .build();
            user.resetPassword("Lxzzlxz5@");
            userRepo.insert(user);
        }
    }

}
