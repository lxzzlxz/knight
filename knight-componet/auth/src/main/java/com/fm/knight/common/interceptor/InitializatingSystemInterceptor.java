package com.fm.knight.common.interceptor;

import java.util.Date;

import com.fm.knight.model.User;
import com.fm.knight.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class InitializatingSystemInterceptor implements ApplicationListener<ContextRefreshedEvent>{
    @Autowired
    private UserRepository userRepo;
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(userRepo.findAll().size() == 0) {
            // 初始化用户
            var user = User.builder()
            .userName("lzm")
            .fullName("刘泽民")
            .gender(2)
            .state(1)
            .lastUpdatePwdDate(new Date())
            .createDate(new Date())
            .build();
            user.resetPassword("1");
            userRepo.save(user);
        }
    }

}
