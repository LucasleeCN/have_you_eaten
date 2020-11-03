package com.fanfou.food.controller;

import com.fanfou.food.controller.form.RegisterForm;
import com.fanfou.food.dao.repo.UserRepository;
import com.fanfou.food.domain.ResultData;
import com.fanfou.food.service.IUserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author smc
 * @date 2020/10/28 22:25
 */
class UserControllerTest {
    @InjectMocks
    private UserController userController;

    private BindingResult bindingResult;

    @Test
    /*
    规范的用户名和密码
     */
    void register1() throws Exception{
        String username = "abc";
        String password = "123";
        RegisterForm registerForm = new RegisterForm();
        registerForm.setUsername(username);
        registerForm.setPassword(password);
        ResultData result = userController.register(registerForm,bindingResult);
    }
    @Test
    /*
    空用户名
     */
    void register2() {
        String username = "";
        String password = "123";
        RegisterForm registerForm = new RegisterForm();
        registerForm.setUsername(username);
        registerForm.setPassword(password);
    }
    @Test
    /*
    空密码
     */
    void register3() {
        String username = "abc";
        String password = "";
        RegisterForm registerForm = new RegisterForm();
        registerForm.setUsername(username);
        registerForm.setPassword(password);
    }
}