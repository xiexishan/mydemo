package com.example.mytttptestpro;
import com.example.mytttptestpro.utils.EmailApi;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = MytttptestproApplication.class)
class MytttptestproApplicationTests {

    @Resource
    private EmailApi emailApi;
    @Test
    void contextLoads() {
    }
    @Test
    void emailsendtest(){
        //System.out.println(emailApi);
        emailApi.sendGeneralEmail("测试","<h1>测试邮件</h1>","tljib484@gmail.com");
    }


}
