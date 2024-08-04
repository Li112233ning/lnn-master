package com.lnn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnn.entity.UserRole;
import com.lnn.mapper.UserRoleMapper;
import com.lnn.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户-角色(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2024-08-04 19:36:14
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}


