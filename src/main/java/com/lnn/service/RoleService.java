package com.lnn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lnn.entity.Role;

import java.util.List;

/**
 * 角色(Role)表服务接口
 *
 * @author makejava
 * @since 2024-08-04 19:45:16
 */
public interface RoleService extends IService<Role> {

    List<Role> getSelectedRolesByMenuId(Long menuId);

    List<Role> getSelectedRolesByUserId(Long userId);

}


