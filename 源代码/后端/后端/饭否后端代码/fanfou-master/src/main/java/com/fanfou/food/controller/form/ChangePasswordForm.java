package com.fanfou.food.controller.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jzw
 * @date 2020/11/1 10:10
 */
@Data
public class ChangePasswordForm {
    @NotNull(message = "用户Id不能为空")
    private Long userId;

    @NotNull(message = "旧密码不能为空")
    private String oldPassword;

    @NotNull(message = "新密码不能为空")
    private String newPassword;
}
