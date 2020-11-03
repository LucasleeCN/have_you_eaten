package com.fanfou.food.exceptions;

import lombok.*;

/**
 * @author jzw
 * @date 2020/10/27 14:05
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthException extends Exception {
    public static final int USER_NOT_EXISTS = 1;
    public static final int PASSWORD_NOT_MATCH_USERNAME = 2;
    public static final int USER_IS_EXISTS = 3;
    public static final int USER_PASSWORD_ERROR = 4;
    public static final int TOKEN_NULL_MESSAGE = 5;
    public static final int TOKEN_DECODE_ERROR_MESSAGE = 6;
    public static final int TOKEN_USER_ERROR_MESSAGE = 7;

    private int code;

    @Override
    public String getMessage() {
        switch (code) {
            case USER_NOT_EXISTS:
                return "用户不存在";
            case PASSWORD_NOT_MATCH_USERNAME:
                return "用户名和密码不匹配";
            case USER_IS_EXISTS:
                return "用户名已存在";
            case USER_PASSWORD_ERROR:
                return "密码错误";
            case TOKEN_NULL_MESSAGE:
                return "token不存在";
            case TOKEN_DECODE_ERROR_MESSAGE:
                return "token错误";
            case TOKEN_USER_ERROR_MESSAGE:
                return "用户不存在，请重新登录";
            default:
                return "";
        }
    }
}
