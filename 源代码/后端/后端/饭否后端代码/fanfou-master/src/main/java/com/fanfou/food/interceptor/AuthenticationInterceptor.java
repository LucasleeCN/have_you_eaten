package com.fanfou.food.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fanfou.food.annotation.UserLoginToken;
import com.fanfou.food.dao.entity.UserEntity;
import com.fanfou.food.dao.repo.UserRepository;
import com.fanfou.food.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author jzw
 * @date 2020/11/1 10:29
 */
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final String TOKEN_NULL_MESSAGE = "token不存在";
    private static final String TOKEN_DECODE_ERROR_MESSAGE = "token错误";
    private static final String TOKEN_USER_ERROR_MESSAGE = "用户不存在，请重新登录";
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("token: " + token);
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 如果存在不需要验证的方法直接通过验证
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException(TOKEN_NULL_MESSAGE);
                }
                // 获取 token 中的 userId
                long userId;
                try {
                    userId = Long.parseLong(JWT.decode(token).getAudience().get(0));
                } catch (JWTDecodeException j) {
                    throw new RuntimeException(TOKEN_DECODE_ERROR_MESSAGE);
                }
                Optional<UserEntity> optionalUserEntity = this.userRepository.findById(userId);
                if (!optionalUserEntity.isPresent()) {
                    throw new RuntimeException(TOKEN_USER_ERROR_MESSAGE);
                }
                UserEntity userEntity = optionalUserEntity.get();
                User user = new User();
                BeanUtils.copyProperties(userEntity, user);
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUsername())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException(TOKEN_DECODE_ERROR_MESSAGE);
                }
                request.setAttribute("user", user);
                return true;
            }
            return false;
        }
        return true;
    }

}