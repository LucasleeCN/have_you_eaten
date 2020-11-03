package com.fanfou.food.controller.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jzw
 * @date 2020/10/27 14:19
 */
@Data
public class LoginForm {

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;
}
