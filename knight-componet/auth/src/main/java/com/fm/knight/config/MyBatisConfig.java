package com.fm.knight.config;
import com.fm.knight.dao.UserMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {

    @Bean
    public MapperFactoryBean<UserMapper> userMapper(SqlSessionFactory sqlSessionFactory) {
        // 1. 创建 MapperFactoryBean 实例
        MapperFactoryBean<UserMapper> factoryBean = new MapperFactoryBean<>();

        // 2. 设置 Mapper 接口类型（必须）
        factoryBean.setMapperInterface(UserMapper.class);

        // 3. 设置 SqlSessionFactory（必须）
        factoryBean.setSqlSessionFactory(sqlSessionFactory);

        return factoryBean;
    }
}
