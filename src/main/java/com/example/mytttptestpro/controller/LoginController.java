package com.example.mytttptestpro.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.mytttptestpro.entity.Result;
import com.example.mytttptestpro.entity.User;
import com.example.mytttptestpro.entity.UserParam;
import com.example.mytttptestpro.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/home")
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @GetMapping("/login")
    public String Login(){
        return "redirect:/pages/login.html";
    }

    @RequestMapping("/register")
    @ResponseBody
    public Result registerUser(UserParam user ){
        String email = user.getEmail();
        String emailCode = user.getEmailCode();
        BoundHashOperations<String, String, String> hashOps = stringRedisTemplate.boundHashOps("login:email:captcha:"+email);
        String code = hashOps.get("captcha");
        if(!Objects.equals(code, emailCode)){
            return new Result("验证码错误",400,null);
        }
        User user1 = userService.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getAccount,user.getAccount()));
        //如果有这个用户的信息要拒绝注册
        if(user1!=null){
            return new Result("",100,null);
        }
        User saveUser = new User();
        BeanUtils.copyProperties(user,saveUser);
        System.out.println(user);
        System.out.println(saveUser);
        boolean save = userService.save(saveUser);
        if(!save){
            return new Result("注册失败",300,null);
        }
        return new Result("注册成功",200,null);
    }
}
