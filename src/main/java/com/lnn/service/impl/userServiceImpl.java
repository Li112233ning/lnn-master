package com.lnn.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnn.entity.Role;
import com.lnn.mapper.UserMapper;
import com.lnn.service.UserService;
import com.lnn.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class userServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    //重写UserDetailsService里面的loadUserByUsername
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名从数据库获取用户信息
        User user = userMapper.getByUsername(username);

        // 断言：如果用户不为空，则抛出异常，提示用户名未找到
        Assert.notNull(user, "用户名未找到!");

        // 将数据库中的角色拆分成SpringSecurity所需的权限结构
        String roles = user.getRoles().stream()
                .map(Role::getTag)
                .collect(Collectors.joining(","));

        // 将角色字符串转换为SpringSecurity的权限列表，并设置到用户对象中
        user.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(roles));

        // 返回实现了 UserDetails 接口的用户对象
        return user;
    }


//    @Override
//    public IPage<User> listPage(IPage<User> page, Wrapper<User> queryWrapper) {
//        return null;
//    }


}
