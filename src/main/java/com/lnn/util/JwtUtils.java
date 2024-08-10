package com.lnn.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.lnn.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

public class JwtUtils {
    // 定义 JWT 加密的密钥
    private static final String secret = "111111";

    /**
     * 生成 JWT token
     * @param authentication 认证信息
     * @return 生成的 token
     */
    public static String token(Authentication authentication) {
        // 创建并返回 JWT token
        return JWT.create()
                // 设置过期时间: 当前时间加上 30 天（单位为毫秒）
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30))
                // 设置接受方信息，一般为登录用户的认证信息
                .withAudience(JSON.toJSONString(authentication))
                // 使用 HMAC256 算法和密钥进行签名
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 根据指定日期返回 token
     * @param authentication 认证信息
     * @param time 过期时间（单位毫秒）
     * @return 返回生成的 token
     */
    public static String token(Authentication authentication, Long time) {
        // 创建并返回 JWT token
        return JWT.create()
                // 设置过期时间: 当前时间加上指定的时间（单位为毫秒）
                .withExpiresAt(new Date(System.currentTimeMillis() + time))
                // 设置接受方信息
                .withAudience(JSON.toJSONString(authentication))
                // 使用 HMAC256 算法和密钥进行签名
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 验证 token 的合法性
     * @param token 要验证的 token
     */
    public static void tokenVerify(String token) {
        // 创建 JWT 验证器
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        // 验证 token，若无异常则验证成功
        jwtVerifier.verify(token);

        // 获取 token 的过期时间
        JWT.decode(token).getExpiresAt();
        // 获取 token 中的接受方信息（JSON 字符串）
        String json = JWT.decode(token).getAudience().get(0);

        // 将 JSON 字符串转换为 JwtAuthentication 对象
        JwtAuthentication jwtAuthentication = JSON.parseObject(json, JwtAuthentication.class);

        // 将认证信息设置到 Spring Security 的上下文中
        SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
    }

    /**
     * 刷新 token
     * @param token 要刷新的 token
     * @return 刷新后的 token
     */
    public static String refreshToken(String token) {
        // 验证传入的 token
        JwtUtils.tokenVerify(token);

        // 获取当前的认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 生成并返回新的 token
        return JwtUtils.token(authentication);
    }
}
