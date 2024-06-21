package com.atguigu.lease.common.context;

public class AdminUserContext {

    private static final ThreadLocal<AdminUser> threadLocal = new ThreadLocal<>();

    public static void set(AdminUser user) {
        threadLocal.set(user);
    }

    public static AdminUser get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
