package com.lnn.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnn.entity.Menu;
import com.lnn.mapper.MenuMapper;
import com.lnn.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Override
    public List<Menu> listMenu(QueryWrapper wrapper) {
        return menuMapper.listMenu(wrapper);
    }

    @Override
    public IPage<Menu> listPage(IPage<Menu> page, Wrapper<Menu> queryWrapper) {
        return menuMapper.listPage(page, queryWrapper);
    }
}
