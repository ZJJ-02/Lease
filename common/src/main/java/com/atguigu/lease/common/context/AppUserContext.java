package com.atguigu.lease.common.context;

public class AppUserContext {

    private static final ThreadLocal<AppUser> threadLocal = new ThreadLocal<>();

    public static void set(AppUser user) {
        threadLocal.set(user);
    }

    public static AppUser get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
