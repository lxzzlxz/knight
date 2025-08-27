package com.fm.knight.knight.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.cloud.commons.util.InetUtilsProperties;

@Configuration
public class NacosConfig {
    @Bean
    @Primary
    public InetUtilsProperties customInetUtilsProperties() {
        return new InetUtilsProperties();
    }
}
