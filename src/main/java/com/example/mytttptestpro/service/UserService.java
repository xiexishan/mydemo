package com.example.mytttptestpro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mytttptestpro.entity.User;

public interface UserService extends IService<User> {
    public User login(String account,String password);
}
