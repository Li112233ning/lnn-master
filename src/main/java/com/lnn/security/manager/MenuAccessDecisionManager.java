package com.lnn.security.manager;

import cn.hutool.core.collection.CollUtil;
import com.lnn.entity.Perm;
import com.lnn.entity.User;
import com.lnn.mapper.UserMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 权限判断
 */
@Slf4j
@Component
public class MenuAccessDecisionManager implements AccessDecisionManager {

    @Autowired
    private UserMapper userMapper;

    @SneakyThrows
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> collection) {
        // 当前请求需要的权限
        log.info("collection:{}", collection);
        // 当前用户所具有的权限
        log.info("principal:{}", authentication.getPrincipal().toString());
        Object principal = authentication.getPrincipal();
        String requestURI = ((FilterInvocation) object).getRequest().getRequestURI();

        for (ConfigAttribute configAttribute : collection) {
            String needRole = configAttribute.getAttribute();
            if("GUEST".equals(needRole)){//游客访问
                return;
            }
        }

        if(principal instanceof String && !"/login".equals(requestURI)){
            throw new BadCredentialsException("未登录");
        }

        // 当前用户所具有的权限
        User loginUser= (User) authentication.getPrincipal();
        User user = userMapper.getByUsername(loginUser.getUsername());
        for (ConfigAttribute configAttribute : collection) {
            // 当前请求需要的权限
            String needRole = configAttribute.getAttribute();
            if ("NO_AUTH".equals(needRole)) {
                throw new AccessDeniedException(user.getUsername()+",没有访问:"+requestURI+"的权限");
            }

            if(CollUtil.isNotEmpty(user.getPerms())){
                for (Perm perm : user.getPerms()) {
                    // 包含其中一个权限即可访问
                    if(perm.getTag().equals(needRole)){
                        return;
                    }
                }
            }

        }
        throw new AccessDeniedException(user.getUsername()+",没有访问:"+requestURI+"的权限");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
