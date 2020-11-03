package com.fanfou.food.service.impl;

import com.fanfou.food.controller.form.LoginForm;
import com.fanfou.food.controller.form.RegisterForm;
import com.fanfou.food.dao.entity.UserEntity;
import com.fanfou.food.dao.repo.UserRepository;
import com.fanfou.food.domain.User;
import com.fanfou.food.exceptions.AuthException;
import com.fanfou.food.service.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author smc
 * @date 2020/10/27 19:07
 */
@SpringBootTest
@ActiveProfiles("test")
class UserServiceImplTest {
    @Autowired
    private IUserService userService;
    @Autowired
    private UserRepository userRepository;


    /**
     * 测试注册成功
     */
    @Test
    void register() {
        String username = "abc";
        String password = "123";
        RegisterForm registerForm = new RegisterForm();
        registerForm.setUsername(username);
        registerForm.setPassword(password);
        try {
            userService.register(registerForm);
        } catch (AuthException e) {
            e.printStackTrace();
        }
        Optional<UserEntity> optionalUserEntity = this.userRepository.findUserEntityByUsername("abc");
        assertTrue(optionalUserEntity.isPresent());
        assertEquals(optionalUserEntity.get().getUsername(), "abc");
    }
    /**
     * 测试注册重复用户名
     */


    @Test
    void register2() {
        String username1 = "abc3";
        String password1 = "1233";
        String username2 = "abc3";
        String password2 = "1233";
        RegisterForm registerForm1 = new RegisterForm();
        RegisterForm registerForm2 = new RegisterForm();

        registerForm1.setUsername(username1);
        registerForm1.setPassword(password1);

        Assertions.assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.register(registerForm1);
            }
        });

        registerForm2.setUsername(username2);
        registerForm2.setPassword(password2);

        Optional<UserEntity> optionalUserEntity = this.userRepository.findUserEntityByUsername("abc1");
        assertTrue(optionalUserEntity.isPresent());
        assertEquals(optionalUserEntity.get().getUsername(), "abc1");


        try {
            userService.register(registerForm1);
        } catch (AuthException e) {
            Assertions.assertEquals(e.getCode(),AuthException.USER_IS_EXISTS);
        }



    }

    @Test
    /*
    登录成功
     */
    void login1() {
        String username = "abc";
        String password = "123";
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername(username);
        loginForm.setPassword(password);
        try {
            userService.login(loginForm);
        } catch (AuthException e) {
            e.printStackTrace();
        }
    }
    @Test
    /*
    用户名不存在
     */
    void login2() {
        String username = "a";
        String password = "123";
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername(username);
        loginForm.setPassword(password);

        try {
            userService.login(loginForm);
        } catch (AuthException e) {
            Assertions.assertEquals(e.getCode(),AuthException.USER_NOT_EXISTS);
        }
    }

    @Test
    /*
    密码错误
     */
    void login3() {
        String username = "abc";
        String password = "111";
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername(username);
        loginForm.setPassword(password);

        try {
            User user=userService.login(loginForm);
            Assertions.assertEquals(user.getName(),"abc");
        } catch (AuthException e) {
            Assertions.assertEquals(e.getCode(),AuthException.PASSWORD_NOT_MATCH_USERNAME);
        }
    }

}