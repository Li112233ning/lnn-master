package com.lnn.security.handler;


import com.lnn.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NoAuthAccessDeniedHandler implements AccessDeniedHandler {

    @SneakyThrows
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)  {
        log.info("认证未通过:{}",accessDeniedException.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(500);
        response.getWriter().write(Result.errorJSON(accessDeniedException.getMessage()));
    }
}