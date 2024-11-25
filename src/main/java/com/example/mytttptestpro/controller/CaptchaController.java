package com.example.mytttptestpro.controller;

import com.example.mytttptestpro.entity.Result;
import com.example.mytttptestpro.service.CaptchaService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {
    @Resource
    CaptchaService captchaService;
    @RequestMapping("/getCaptcha")
    public Result sendCaptcha(String email){
        boolean res = captchaService.sendCaptcha(email);
        if(res){
            return Result.success();
        }
        return Result.fail();
    }

}
