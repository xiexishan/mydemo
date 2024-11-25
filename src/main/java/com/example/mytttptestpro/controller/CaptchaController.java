package com.example.mytttptestpro.controller;

import com.example.mytttptestpro.entity.Result;
import com.example.mytttptestpro.service.CaptchaService;
import com.example.mytttptestpro.utils.EmailApi;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {
    @Resource
    CaptchaService captchaService;
    @Resource
    EmailApi emailApi;
    @RequestMapping("/getCaptcha")
    public Result sendCaptcha(String email){
        boolean res = captchaService.sendCaptcha(email);
        if(res){
            return new Result("发送成功",200,null);
        }
        return new Result("发送失败",500,null);
    }

}
