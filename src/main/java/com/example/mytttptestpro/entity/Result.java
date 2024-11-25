package com.example.mytttptestpro.entity;



public class Result<T> {
    private String message; // 响应消息
    private int code;       // 响应状态码
    private T data;         // 泛型数据

    // 全参构造器
    public Result(String message, int code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    // 无参构造器（方便 Jackson 等工具反序列化）
    public Result() {
    }

    // Getter 和 Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // 静态方法快速生成 Result 实例
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(message, 200, data);
    }

    public static <T> Result<T> error(String message, int code) {
        return new Result<>(message, code, null);
    }
}
