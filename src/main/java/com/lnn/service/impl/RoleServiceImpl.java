package com.lnn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnn.mapper.RoleMapper;
import com.lnn.entity.Role;
import com.lnn.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色(Role)表服务实现类
 *
 * @author makejava
 * @since 2024-08-04 19:45:16
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> getSelectedRolesByMenuId(Long menuId) {
        return roleMapper.getSelectedRolesByMenuId(menuId);
    }

    @Override
    public List<Role> getSelectedRolesByUserId(Long userId) {
        return roleMapper.getSelectedRolesByUserId(userId);
    }

}


