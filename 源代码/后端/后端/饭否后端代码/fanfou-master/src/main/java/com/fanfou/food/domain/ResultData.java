package com.fanfou.food.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jzw
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultData implements Serializable {
    private static final Integer SUCCESS_CODE = 0;
    private static final Integer FAIL_CODE = -1;
    private Integer code;
    private Object data;
    private String message;

    public static ResultData success(String message) {
        return success(null, message);
    }

    public static ResultData success(Object o, String message) {
        ResultData resultData = new ResultData();
        resultData.setCode(ResultData.SUCCESS_CODE);
        resultData.setData(o);
        resultData.setMessage(message);
        return resultData;
    }

    public static ResultData success() {
        return success("");
    }

    public static ResultData success(Object o) {
        return success(o, "");
    }

    public static ResultData fail(Object o, String message) {
        ResultData resultData = new ResultData();
        resultData.setCode(FAIL_CODE);
        resultData.setData(o);
        resultData.setMessage(message);
        return resultData;
    }

    public static ResultData fail(String message) {
        return fail(null, message);
    }

    public static ResultData fail() {
        return fail("");
    }
}
