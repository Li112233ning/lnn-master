package com.lnn.security.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lnn.common.Constant;
import com.lnn.entity.Menu;
import com.lnn.entity.Perm;
import com.lnn.entity.User;
import com.lnn.mapper.MenuMapper;
import com.lnn.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class MyAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    MenuMapper menuMapper;
    @Autowired
    UserMapper userMapper;


    // 调用父类的 verify 方法，进行默认的验证
    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {
        // 检查用户是否为匿名用户，如果是，则抛出访问拒绝异常
        if (!(authentication.get().getPrincipal() instanceof User)) {
            throw new AccessDeniedException("匿名不能访问");
        }

        User user = (User)authentication.get().getPrincipal();

        // 从数据库中根据用户名获取用户信息
        user = userMapper.getByUsername(user.getUsername());

        // 获取当前登录用户的权限列表
        List<String> auths = user.getPerms().stream()
                .map(Perm::getTag)
                .collect(Collectors.toList());

        // 获取请求的接口地址
        String requestURI = requestAuthorizationContext.getRequest().getRequestURI();

        // 如果请求的地址在匿名访问列表中，允许直接访问
        if (Constant.annosList.contains(requestURI)) {
            return new AuthorizationDecision(true);
        }

        // 查询当前请求的接口需要哪些权限才能访问
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("m.type", 2); // 查询类型为 2 的菜单
        queryWrapper.eq("m.path", requestURI); // 查询路径与请求 URI 匹配的菜单
        List<Menu> menus = menuMapper.listMenu(queryWrapper); // 获取符合条件的菜单列表


        // 检查用户权限是否符合菜单权限
        for (String auth : auths) {
            for (Menu menu : menus) {
                // 获取菜单的权限列表
                List<String> menuPerms = menu.getPerms().stream()
                        .map(Perm::getTag)
                        .collect(Collectors.toList());
                // 如果菜单权限中包含用户权限，返回授权通过
                if (menuPerms.contains(auth)) {
                    return new AuthorizationDecision(true);
                }
            }
        }

        // 如果没有找到合适的权限，抛出访问拒绝异常
        throw new AccessDeniedException(user.getUsername() + ",没有访问:" + requestURI + "的权限");
    }
}
