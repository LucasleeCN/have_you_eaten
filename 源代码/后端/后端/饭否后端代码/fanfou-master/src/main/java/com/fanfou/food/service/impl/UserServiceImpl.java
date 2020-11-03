package com.fanfou.food.service.impl;

import com.fanfou.food.controller.form.LoginForm;
import com.fanfou.food.controller.form.RegisterForm;
import com.fanfou.food.dao.entity.UserEntity;
import com.fanfou.food.dao.repo.UserRepository;
import com.fanfou.food.domain.User;
import com.fanfou.food.exceptions.AuthException;
import com.fanfou.food.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author jzw
 * @date 2020/10/27 13:45
 */
@Service("UserService")
public class UserServiceImpl implements IUserService {
    final UserRepository userRepository;
    final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public User register(RegisterForm registerForm) throws AuthException {
        Optional<UserEntity> optionalUserEntity = this.userRepository.findUserEntityByUsername(registerForm.getUsername());
        if (optionalUserEntity.isPresent()) {
            throw new AuthException(AuthException.USER_IS_EXISTS);
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(registerForm, userEntity);
//        userEntity.setPassword(encoder.encode(registerForm.getPassword()));
        userEntity = this.userRepository.save(userEntity);
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        return user;
    }

    @Override
    public User login(LoginForm loginForm) throws AuthException {
        Optional<UserEntity> optionalUserEntity = userRepository.findUserEntityByUsername(loginForm.getUsername());
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
//            if (!loginForm.getPassword().equals(userEntity.getPassword())) {
//                throw new AuthException(AuthException.PASSWORD_NOT_MATCH_USERNAME);
//            }
            if (!encoder.matches(loginForm.getPassword(), userEntity.getPassword())) {
                // 密码不正确
                throw new AuthException(AuthException.PASSWORD_NOT_MATCH_USERNAME);
            }
            User user = new User();
            BeanUtils.copyProperties(userEntity, user);
            return user;
        } else {
            // 用户不存在
            throw new AuthException(AuthException.USER_NOT_EXISTS);
        }
    }

    @Override
    public User getOneUser(Long userId) {
        UserEntity userEntity = userRepository.getOne(userId);
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        return user;
    }

    @Override
    public boolean changePassword(Long userId, String oldPassword, String newPassword) throws AuthException {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        if (!optionalUserEntity.isPresent()) {
            throw new AuthException(AuthException.USER_NOT_EXISTS);
        }
        UserEntity userEntity = optionalUserEntity.get();
        if (!encoder.matches(oldPassword, userEntity.getPassword())) {
            throw new AuthException(AuthException.USER_PASSWORD_ERROR);
        }
        userEntity.setPassword(encoder.encode(newPassword));
        userRepository.save(userEntity);
        return true;
    }
}
