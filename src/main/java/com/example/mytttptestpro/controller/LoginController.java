package com.example.mytttptestpro.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.mytttptestpro.entity.*;
import com.example.mytttptestpro.service.UserService;
import com.example.mytttptestpro.utils.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/home")
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @GetMapping("/login")
    public Result Login(@RequestBody UserParam userParam){
        User user=userService.login(userParam.getAccount(),userParam.getPassword());
        if(user==null)return Result.fail("用户不存在");
        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("userAccount",userParam.getAccount());
        String token = JwtUtil.createJWT(
                jwtProperties.getSecret(),
                jwtProperties.getExpiration(),
                claims);
        UserVo userVo=new UserVo(userParam.getAccount(),userParam.getEmail(),token);
        return Result.success(userVo);
    }
    @RequestMapping("/register")
    @ResponseBody
    public Result registerUser(UserParam user){
        String email = user.getEmail();
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
//        System.out.println(user);
//        System.out.println(saveUser);
        boolean save = userService.save(saveUser);
        if(!save){
            return Result.fail("保存失败");
        }
        return Result.success();
    }

}
