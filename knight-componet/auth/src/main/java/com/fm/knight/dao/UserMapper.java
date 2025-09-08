package com.fm.knight.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fm.knight.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 刘泽民
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
