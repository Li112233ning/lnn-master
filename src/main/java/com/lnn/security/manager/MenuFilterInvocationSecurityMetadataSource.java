package com.lnn.security.manager;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lnn.entity.Menu;
import com.lnn.entity.Role;
import com.lnn.mapper.MenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 动态获取URI权限配置
 */
@Slf4j
@Component
public class MenuFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 根据请求的URI去查询该URL需要的所有权限 返回:再通过AccessDecisionManager中判断该用户是否具有这些权限
     * @param object 被保护的对象
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) {
        Set<ConfigAttribute> set = new HashSet<>();

        String requestURI = ((FilterInvocation) object).getRequest().getRequestURI();

        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("m.type",2);
        queryWrapper.eq("m.path",requestURI);
        List<Menu> menus = menuMapper.listMenu(queryWrapper);//查询当前请求的URI需要哪些权限能访问

        if (CollUtil.isNotEmpty(menus)) {

            if(isGuest(menus)){
                return SecurityConfig.createList("GUEST");//游客访问
            }

            List<String> perms = new ArrayList<>();
            menus.stream().forEach(e-> {
                if(CollUtil.isNotEmpty(e.getPerms())) {
                    e.getPerms().stream().forEach(m -> perms.add(m.getTag()));
                }
            });

            if(CollUtil.isNotEmpty(perms)){
                for (String perm : perms) {
                    SecurityConfig securityConfig = new SecurityConfig(perm);
                    set.add(securityConfig);
                }
            }

        }

        if (ObjectUtils.isEmpty(set) ) {
            return SecurityConfig.createList("NO_AUTH");//未授权
        }

        return set;
    }

    public boolean isGuest(List<Menu> menus){

        for (Menu menu : menus) {
            if(CollUtil.isNotEmpty(menu.getRoles())){
                for (Role role : menu.getRoles()) {
                    if(role.getTag().equalsIgnoreCase("GUEST")){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
