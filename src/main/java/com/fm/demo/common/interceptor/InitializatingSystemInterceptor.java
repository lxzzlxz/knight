package com.fm.demo.common.interceptor;

import java.util.Date;

import javax.transaction.Transactional;

import com.fm.demo.system.user.model.User;
import com.fm.demo.system.user.repository.UserRepository;

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
            .username("lzm")
            .fullname("刘泽民")
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
