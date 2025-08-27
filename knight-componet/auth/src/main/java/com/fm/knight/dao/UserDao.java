package com.fm.knight.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fm.knight.model.User;
import org.apache.ibatis.annotations.Mapper;

// 确保使用最新的MyBatis-Plus注解方式
@Mapper
public interface UserDao extends BaseMapper<User> {
}
