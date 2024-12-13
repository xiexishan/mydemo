package com.example.mytttptestpro.test;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.io.InputStream;

public class myokhttp {
    //同步请求
    public void getSync(){
        OkHttpClient httpClient=okhttpconfig.getHttp();
        String url = "https://www.baidu.com/";
        Request getRequest = new Request.Builder()
                .url(url)
                .get()
                .build();
        //准备好请求的Call对象
        Call call = httpClient.newCall(getRequest);
        try {
            Response response = call.execute();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    //异步请求
    public void getAsync() {
        OkHttpClient httpClient=okhttpconfig.getHttp();
        String url = "https://www.baidu.com/";
        Request getRequest = new Request.Builder()
                .url(url)
                .get()
                .build();
        //准备好请求的Call对象
        Call call = httpClient.newCall(getRequest);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String string = body.string();
                byte[] bytes = body.bytes();
                InputStream inputStream = body.byteStream();
            }
        });
    }

    //同步请求
    public void postSync(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient httpClient=okhttpconfig.getHttp();
                FormBody formBody=new FormBody.Builder()
                        .add("a","1")
                        .add("b","2")
                        .build();
                Request request=new Request.Builder()
                        .post(formBody)
                        .url("https://www.httpbin.org/post")
                        .build();
                //准备好请求的Call对象
                Call call = httpClient.newCall(request);
                try {
                    Response response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //异步请求
    public void postAsync(){
        OkHttpClient httpClient=okhttpconfig.getHttp();
        FormBody formBody=new FormBody.Builder()
                .add("a","1")
                .add("b","2")
                .build();
        Request request=new Request.Builder()
                .post(formBody)
                .url("https://www.httpbin.org/post")
                .build();
        //准备好请求的Call对象
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    //Log.i("postAsync",response.body().string());
                }
            }
        });
    }



}
