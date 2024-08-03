package com.lnn.security.handler;

import com.lnn.entity.Role;
import com.lnn.entity.User;
import com.lnn.util.DateUtils;
import com.lnn.util.JwtUtils;
import com.lnn.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j // 使用 Lombok 的 @Slf4j 注解，自动生成日志记录器
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication auth) throws IOException {
        // 从 Authentication 对象中获取用户信息
        User user = (User) auth.getPrincipal();

        // 生成 JWT token
        String token = JwtUtils.token(auth);
        // 将 token 添加到响应头中
        response.addHeader("token", token);

        // 设置响应内容类型为 JSON
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> data = new HashMap<>();
        data.put("username", user.getUsername());
        data.put("roles", user.getRoles().stream().map(Role::getTag).collect(Collectors.toList()));
        data.put("accessToken", token);
        data.put("refreshToken", token);
        data.put("expires", DateUtils.format(DateUtils.addDay(30)));

        log.info("登录成功 {}", user.getUsername());

        // 将响应数据转换为 JSON 格式并写入响应体
        response.getWriter().write(Result.okJSON(data));
    }
}

