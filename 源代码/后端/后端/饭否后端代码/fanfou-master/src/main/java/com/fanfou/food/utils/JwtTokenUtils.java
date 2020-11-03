package com.fanfou.food.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fanfou.food.domain.User;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jzw
 * @date 2020/10/27 14:29
 */
public class JwtTokenUtils {
    public static String getToken(User user) {
        String userId = user.getId().toString();
        String username = user.getUsername();
        return JWT.create().withAudience(userId)
                .sign(Algorithm.HMAC256(username));
    }

    public static Integer getUserId(HttpServletRequest request) {
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return Integer.valueOf(JWT.decode(jwtToken).getAudience().get(0));
    }
}
