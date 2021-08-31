package com.njtech.blog.utils;

import com.njtech.blog.entity.MsSysUser;

public class UserThreadLocal {
    private UserThreadLocal() {
    }

    private final static ThreadLocal<MsSysUser> local = new ThreadLocal<>();

    public static void setLocal(MsSysUser msSysUser){
        local.set(msSysUser);
    }

    public static MsSysUser getLocal(){
        return local.get();
    }
    public static void removeLocal(){
        local.remove();
    }
}
