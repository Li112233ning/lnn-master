package com.lnn.config;

import com.lnn.common.Constant;
import com.lnn.security.auth.MyAuthorizationManager;
import com.lnn.security.filter.JwtAuthenticationTokenFilter;
import com.lnn.security.filter.LoginAuthenticationFilter;
import com.lnn.security.handler.LoginFailureHandler;
import com.lnn.security.handler.LoginSuccessHandler;
import com.lnn.security.handler.NoAuthAccessDeniedHandler;
import com.lnn.security.manager.MenuAccessDecisionManager;
import com.lnn.security.manager.MenuFilterInvocationSecurityMetadataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Autowired
    MenuAccessDecisionManager menuAccessDecisionManager;
    @Autowired
    MenuFilterInvocationSecurityMetadataSource menuFilterInvocationSecurityMetadataSource;
    @Autowired
    NoAuthAccessDeniedHandler noAuthAccessDeniedHandler;
    @Autowired
    private MyAuthorizationManager authorizationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // 禁用 CSRF 保护
        http.csrf(e -> e.disable());
        // 禁用 CORS 保护
        http.cors(e -> e.disable());

        // 配置请求授权规则，允许匿名访问的请求，其他请求使用自定义的授权管理器
        http.authorizeHttpRequests(e -> e.requestMatchers(Constant.annos).permitAll()
                .anyRequest().access(authorizationManager));

        // 添加 JWT 拦截器
        http.addFilterBefore(new JwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        // 重写登录过滤器
        http.addFilterAt(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        // 配置表单登录的成功和失败处理器
        http.formLogin(e -> e.successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailureHandler()));
        // 配置异常处理，使用自定义的无权限处理器
        http.exceptionHandling(e -> e.accessDeniedHandler(noAuthAccessDeniedHandler));

        // 构建并返回 SecurityFilterChain 对象
        return http.build();
    }


    @Autowired // 自动注入 AuthenticationConfiguration 依赖
    AuthenticationConfiguration authenticationConfiguration;

    public LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        // 创建自定义登录过滤器
        LoginAuthenticationFilter filter = new LoginAuthenticationFilter();
        // 设置登录成功处理器
        filter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        // 设置登录失败处理器
        filter.setAuthenticationFailureHandler(new LoginFailureHandler());
        // 设置 AuthenticationManager，防止出现空指针异常
        filter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        return filter; // 返回自定义登录过滤器
    }

    @Bean // 声明该方法返回的对象为 Spring 的 Bean
    public PasswordEncoder passwordEncoder() {
        // 创建并返回一个委托密码编码器
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
