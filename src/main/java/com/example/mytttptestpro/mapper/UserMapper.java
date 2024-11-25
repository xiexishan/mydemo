package com.example.mytttptestpro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mytttptestpro.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
