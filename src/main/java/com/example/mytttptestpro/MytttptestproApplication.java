package com.example.mytttptestpro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.example.mytttptestpro.mapper")
@EnableScheduling
@SpringBootApplication
public class MytttptestproApplication {

    public static void main(String[] args) {
        SpringApplication.run(MytttptestproApplication.class, args);
    }

}
