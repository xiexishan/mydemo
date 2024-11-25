package com.example.mytttptestpro.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.mytttptestpro.entity.Result;
import com.example.mytttptestpro.entity.User;
import com.example.mytttptestpro.entity.UserParam;
import com.example.mytttptestpro.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
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
    private RedisTemplate<String, Object> redisTemplate;
    @GetMapping("/login")
    public String Login(){
        return "redirect:/pages/login.html";
    }
    @RequestMapping("/register")
    @ResponseBody
    public Result registerUser(UserParam user){
        String email = user.getAccount();//邮箱
        String emailCode = user.getEmailCode();
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps("login:email:captcha:"+email);
        String code = hashOps.get("captcha");
        if(!Objects.equals(code, emailCode)){
            return Result.fail("验证码错误");
        }
        User user1=userService.getOne(new LambdaQueryWrapper<User>()
                .eq( user.getAccount() != null && !user.getAccount().equals("") ,User::getAccount, user.getAccount()));
        //如果有这个用户的信息要拒绝注册
        if(user1!=null){
            return Result.fail("用户已存在");
        }
        User saveUser = new User();
        BeanUtils.copyProperties(user,saveUser);
        System.out.println(user);
        System.out.println(saveUser);
        boolean save = userService.save(saveUser);
        if(!save){
            return Result.fail("保存失败");
        }
        return Result.success();
    }

}
