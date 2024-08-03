package com.lnn.security.filter;

import cn.hutool.json.JSONUtil;
import com.lnn.util.JwtUtils;
import com.lnn.util.Result;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 登录之前获取token校验:如果有token、再去校验token的合法性:如果没有报错 则认为登录成功
 * 【token是根据SpringSecurity的Authentication生成的,相当于token就是SpringSecurity认证后的凭证】
 */

@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @SneakyThrows//Lombok 的 @SneakyThrows 注解，自动处理异常
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头里的JWT
        String token = request.getHeader("Authorization");
        log.info("接收到的token:{}", token);

        if (token != null){
            try {
                //验证token
                JwtUtils.tokenVerify(token);
            } catch (Exception e){
                //如果 token 验证失败，设置响应状态为 200
                response.setStatus(200);
                // 设置响应内容类型为 JSON
                response.setContentType("application/json;charset=UTF-8");
                // 返回错误信息，提示非法 token
                response.getWriter().write(JSONUtil.toJsonStr(Result.error("非法token")));
                // 返回，终止过滤链
                return;
            }
        }

        // 继续执行过滤链，处理下一个过滤器或目标资源
        filterChain.doFilter(request, response);
    }
}
