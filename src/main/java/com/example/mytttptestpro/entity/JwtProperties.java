package com.example.mytttptestpro.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class JwtProperties {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;
    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;
    @Value("${jwt.headerString}")
    private String headerString;

}
