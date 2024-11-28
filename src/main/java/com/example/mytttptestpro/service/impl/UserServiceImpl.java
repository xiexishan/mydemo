package com.example.mytttptestpro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mytttptestpro.entity.User;
import com.example.mytttptestpro.mapper.UserMapper;
import com.example.mytttptestpro.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String account, String password) {
        User user=userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(account!=null && !account.equals(""),User::getAccount,account)
                .eq(password!=null && !password.equals(""),User::getPassword,password)
        );
        if(user!=null){
            return new User(account);
        }else return null;
    }
}
