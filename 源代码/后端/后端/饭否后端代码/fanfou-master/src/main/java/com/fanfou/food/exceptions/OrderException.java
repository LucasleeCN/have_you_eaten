package com.fanfou.food.exceptions;

import static com.fanfou.food.exceptions.AuthException.USER_NOT_EXISTS;

/**
 * @author jzw
 * @date 2020/10/31 13:29
 */
public class OrderException extends Exception {
    public static final int ORDER_IS_NOT_EXISTS = 1;

    private final int code;

    public OrderException(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        switch (code) {
            case ORDER_IS_NOT_EXISTS:
                return "订单不存在";
            default:
                return "";
        }
    }
}
