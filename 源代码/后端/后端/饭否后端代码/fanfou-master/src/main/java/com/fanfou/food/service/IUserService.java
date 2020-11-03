package com.fanfou.food.service;

import com.fanfou.food.controller.form.LoginForm;
import com.fanfou.food.controller.form.RegisterForm;
import com.fanfou.food.domain.User;
import com.fanfou.food.exceptions.AuthException;

public interface IUserService {

    /**
     * 注册用户
     *
     * @param registerForm 注册表单信息
     * @return
     */
    User register(RegisterForm registerForm) throws AuthException;

    User login(LoginForm loginForm) throws AuthException;

    User getOneUser(Long userId);

    boolean changePassword(Long userId, String oldPassword, String newPassword) throws AuthException;
}
