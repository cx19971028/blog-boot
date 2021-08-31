package com.njtech.blog.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    PARAMS_ERROR(1001,"参数有误"),
    TOKEN_ERROR(1003,"token有误"),
    ACCOUNT_PWD_NOT_EXIST(1002,"用户名或密码不存在"),
    NO_PREMISSION(7001,"无访问权限"),
    SESSION_TIME_OUT(9001,"会话超时"),
    NO_LOGIN(9002,"未登录"),
    ACCOUNT_EXIST(1004,"账户已经被注册");


    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
