package com.lnn.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //重写 尝试身份验证 这个方法
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //判断请求内容是否为json
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE) || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            // 创建 ObjectMapper 实例，用于解析 JSON
            ObjectMapper objectMapper = new ObjectMapper();

            UsernamePasswordAuthenticationToken authenticationToken = null;
            //获取请求输入流
            try (ServletInputStream is = request.getInputStream()) {
                // 将输入流中的 JSON 数据解析为 Map
                Map<String, String> authenticationBean = objectMapper.readValue(is, Map.class);
                // 创建身份验证令牌，包含用户名和密码
                authenticationToken = new UsernamePasswordAuthenticationToken(authenticationBean.get("username"), authenticationBean.get("password"));
            } catch (IOException e) {
                e.printStackTrace();
                // 创建空的身份验证令牌
                authenticationToken = new UsernamePasswordAuthenticationToken("", "");
            } finally {
                // 设置请求的详细信息
                setDetails(request, authenticationToken);

                // 进行身份验证并返回结果
                this.getAuthenticationManager().authenticate(authenticationToken);
                return this.getAuthenticationManager().authenticate(authenticationToken);
            }
        } else {
            // 如果不是 JSON 请求，则调用父类的方法进行身份验证
            return super.attemptAuthentication(request, response);
        }
    }
}
