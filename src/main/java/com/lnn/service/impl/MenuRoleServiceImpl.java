package com.lnn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnn.entity.MenuRole;
import com.lnn.mapper.MenuRoleMapper;
import com.lnn.service.MenuRoleService;
import org.springframework.stereotype.Service;

/**
 * 菜单-角色(MenuRole)表服务实现类
 *
 * @author makejava
 * @since 2024-08-05 00:48:28
 */
@Service("menuRoleService")
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements MenuRoleService {

}


