package com.lnn.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.lnn.entity.*;
import com.lnn.service.*;
import com.lnn.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private PermService permService;
    @Autowired
    private UserPermService userPermService;



    @PostMapping("/register")
    public Result register(@RequestBody User user, HttpServletRequest request) {
        // 根据用户名查询用户，检查该用户是否已存在
        User username = userService.getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        // 如果用户不为空，则抛出异常
        Assert.isNull(username,"用户已存在");

        // 对用户输入的密码进行加密
        String passWord = passwordEncoder.encode(user.getPassword());
        // 设置用户加密后的新密码
        user.setPassword(passWord);
        // 将用户的昵称设置为用户名
        user.setNickname(user.getUsername());
        // 将用户设置为启用状态
        user.setEnabled(true);
        // 保存用户信息到数据库
        userService.save(user);

        // 获取角色为"admin"的角色信息
        Role role = roleService.getOne(new QueryWrapper<Role>().eq("tag", "admin"));
        UserRole userRole = new UserRole();
        // 设置用户角色关联的用户ID
        userRole.setUserId(user.getId());
        // 设置用户角色关联的角色ID
        userRole.setRoleId(role.getId());
        userRoleService.save(userRole);

        // 获取权限为"perm_all"的权限信息
        Perm perm = permService.getOne(new QueryWrapper<Perm>().eq("tag", "perm_all"));
        UserPerm userPerm = new UserPerm();
        // 设置用户权限关联的用户ID
        userPerm.setUserId(user.getId());
        // 设置用户权限关联的权限ID
        userPerm.setPermId(perm.getId());
        userPermService.save(userPerm);

        return Result.ok();

    }
}
