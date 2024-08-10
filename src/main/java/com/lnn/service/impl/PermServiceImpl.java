package com.lnn.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnn.entity.Menu;
import com.lnn.mapper.PermMapper;
import com.lnn.entity.Perm;
import com.lnn.service.PermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限(Perm)表服务实现类
 *
 * @author makejava
 * @since 2024-08-04 19:45:32
 */
@Service("permService")
public class PermServiceImpl extends ServiceImpl<PermMapper, Perm> implements PermService {

    @Autowired
    PermMapper permMapper;

    @Override
    public List<Perm> getSelectedPermsByMenuId(Long menuId) {
        return permMapper.getSelectedPermsByMenuId(menuId);
    }

    @Override
    public List<Perm> getSelectedPermsByUserId(Long userId) {
        return permMapper.getSelectedPermsByUserId(userId);
    }

    @Override
    public IPage<Perm> listPage(IPage<Menu> page, Wrapper<Perm> queryWrapper) {
        return permMapper.listPage(page,queryWrapper);
    }

}


