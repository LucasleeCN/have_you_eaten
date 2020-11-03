package com.fanfou.food.controller.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jzw
 * @date 2020/10/27 13:00
 */
@Data
public class RegisterForm {
    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;

    @NotNull(message = "电话不能为空")
    private String phone;

    @NotNull(message = "名字不能为空")
    private String name;

    private String idenNum;

    @NotNull(message = "用户类型不为空")
    private String type;
}
