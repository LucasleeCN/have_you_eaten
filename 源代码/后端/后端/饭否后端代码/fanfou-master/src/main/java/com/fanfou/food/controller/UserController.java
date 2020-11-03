package com.fanfou.food.controller;

import com.fanfou.food.annotation.UserLoginToken;
import com.fanfou.food.controller.form.ChangePasswordForm;
import com.fanfou.food.controller.form.LoginForm;
import com.fanfou.food.controller.form.RegisterForm;
import com.fanfou.food.domain.ResultData;
import com.fanfou.food.domain.User;
import com.fanfou.food.exceptions.AuthException;
import com.fanfou.food.service.IUserService;
import com.fanfou.food.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Slf4j
public class UserController {
    private static final String REGISTER_SUCCESS_MESSAGE = "注册成功";
    private static final String LOGIN_SUCCESS_MESSAGE = "登录成功";

    final IUserService userService;
    final HttpServletRequest request;

    @Autowired
    public UserController(IUserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

    /**
     * 用户注册
     *
     * @return 用户注册情况
     */
    @PostMapping("/register")
    public ResultData register(@Valid @RequestBody RegisterForm registerForm, BindingResult bindingResult) {
        // 注册数据校验
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                sb.append(error.getDefaultMessage());
            }
            return ResultData.fail(sb.toString());
        }
        try {
            this.userService.register(registerForm);
        } catch (AuthException e) {
            return ResultData.fail(e.getMessage());
        }
        return ResultData.success(REGISTER_SUCCESS_MESSAGE);
    }

    /**
     * 用户登录
     *
     * @return 用户登录情况
     */
    @PostMapping("/login")
    public ResultData login(@Valid @RequestBody LoginForm loginForm, BindingResult bindingResult) {
        log.info(loginForm.toString());
        // 登录数据校验
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                sb.append(error.getDefaultMessage());
            }
            return ResultData.fail(sb.toString());
        }
        Map<String, Object> data = new HashMap<>();
        try {
            User user = this.userService.login(loginForm);
            String token = JwtTokenUtils.getToken(user);
            data.put("token", token);
            data.put("user", user);
        } catch (AuthException e) {
            return ResultData.fail(e.getMessage());
        }
        return ResultData.success(data, LOGIN_SUCCESS_MESSAGE);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("/userinfo")
    @UserLoginToken
    public ResultData getUserInfo() {
        User user = (User)  request.getAttribute("user");
        return ResultData.success(user);
    }

    /**
     * 用户修改密码
     *
     * @param form
     * @return
     */
    @PostMapping("/change-password")
    @UserLoginToken
    public ResultData changePassword(@Valid @RequestBody ChangePasswordForm form) {
        try {
            userService.changePassword(form.getUserId(), form.getOldPassword(), form.getNewPassword());
        } catch (AuthException e) {
            return ResultData.fail(e.getMessage());
        }
        return ResultData.success("密码更改成功");
    }

}
