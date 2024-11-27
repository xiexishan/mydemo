package com.example.mytttptestpro.entity;

import lombok.Data;

@Data
public class UserVo {
    private String Account;
    private String Email;
    private String Token;

    public UserVo(String account, String email, String token) {
        this.Account=account;
        this.Email=email;
        this.Token=token;
    }
}
