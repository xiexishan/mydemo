package com.example.mytttptestpro.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.mytttptestpro.enums.EmailTemplateEnum;
import com.example.mytttptestpro.service.CaptchaService;
import com.example.mytttptestpro.utils.EmailApi;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;


@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    EmailApi emailApi;
    @Override
    public boolean sendCaptcha(String email) {
        //一分钟只能发一条
        sendMailCaptcha("login:email:captcha:"+email);
        return true;
    }

    private boolean sendMailCaptcha(String key){
        BoundHashOperations<String, String, String> hashOps = stringRedisTemplate.boundHashOps(key);
        // 初始检查,判断验证码是否过期，发送次数是否超过5次
        String lastSendTimestamp = hashOps.get("lastSendTimestamp");
        String sendCount = hashOps.get("sendCount");
        if(StringUtils.isNotBlank(sendCount)&&Integer.parseInt(sendCount)>=5){
            hashOps.expire(24, TimeUnit.HOURS);
            throw new  RuntimeException("验证码发送过于频繁");
        }
        if(StringUtils.isNotBlank(lastSendTimestamp)){
            long lastSendTime = Long.parseLong(lastSendTimestamp);
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastSendTime;
            if(elapsedTime < 60 * 1000){
                throw new  RuntimeException("验证码发送过于频繁");
            }
        }
        int newSendCount = StringUtils.isNotBlank(sendCount) ? Integer.parseInt(sendCount) + 1 : 1;
        //验证码
        SecureRandom secureRandom = new SecureRandom();
        int randomCode = secureRandom.nextInt(900000) + 100000;
        String captcha=String.valueOf(randomCode);
        try {
            //发送验证码邮件
            sendCaptcha(key,captcha);
        } catch (Exception e) {
            return false;
        }
        hashOps.put("captcha", captcha);
        hashOps.put("lastSendTimestamp", String.valueOf(System.currentTimeMillis()));
        hashOps.put("sendCount", String.valueOf(newSendCount));
        hashOps.expire(5, TimeUnit.MINUTES); // 设置过期时间为5分钟
        return true;
    }

    private void sendCaptcha(String hashKey, String captcha){
        // 根据hashKey判断是发送邮件还是短信，然后调用相应的发送方法
        if("email".equals(hashKey.split(":")[1])){
            if (!emailApi.sendHtmlEmail(EmailTemplateEnum.VERIFICATION_CODE_EMAIL_HTML.getSubject()
                    , EmailTemplateEnum.VERIFICATION_CODE_EMAIL_HTML.set(captcha)
                    ,hashKey.split(":")[3])) {
                throw new RuntimeException("发送邮件失败");
            }
        }else{
            //TODO 手机验证码
            throw new RuntimeException("格式错误");
        }
    }

}
