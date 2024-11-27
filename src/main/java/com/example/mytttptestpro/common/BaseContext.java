package com.example.mytttptestpro.common;

public class BaseContext {
    // 使用 ThreadLocal 来为每个线程保存独立的值
    private static final ThreadLocal<Object> currentUser = new ThreadLocal<>();

    // 设置当前用户
    public static void setCurrentUser(Object object) {
        currentUser.set(object);
    }

    // 获取当前用户
    public static Object getCurrentUser() {
        return currentUser.get();
    }

    // 清除当前线程中的用户信息
    public static void clear() {
        currentUser.remove();
    }
}
