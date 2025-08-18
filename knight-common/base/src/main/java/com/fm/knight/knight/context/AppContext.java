package com.fm.knight.knight.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
/**
 * ApplicationContextAware 类 用于让 Bean 能够获取到 Spring 的 ApplicationContext 引用
 * Auth: liuzemin
 * date: 2025-08-08
 */

@Component
public class AppContext implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContext.context = applicationContext;
    }

    public static ApplicationContext getContext(){
        return context;
    }
}
