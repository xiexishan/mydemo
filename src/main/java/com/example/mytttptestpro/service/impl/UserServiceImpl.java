package com.example.mytttptestpro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mytttptestpro.entity.User;
import com.example.mytttptestpro.mapper.UserMapper;
import com.example.mytttptestpro.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
