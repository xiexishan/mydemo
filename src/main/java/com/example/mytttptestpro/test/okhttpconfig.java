package com.example.mytttptestpro.test;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class okhttpconfig {
    // 创建日志拦截器
    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    public static OkHttpClient getHttp(){
        OkHttpClient httpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(loggingInterceptor).build();
        return httpClient;
    }
}
