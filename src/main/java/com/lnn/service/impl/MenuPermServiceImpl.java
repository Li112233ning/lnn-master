package com.lnn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnn.entity.MenuPerm;
import com.lnn.mapper.MenuPermMapper;
import com.lnn.service.MenuPermService;
import org.springframework.stereotype.Service;

/**
 * 菜单-权限(MenuPerm)表服务实现类
 *
 * @author makejava
 * @since 2024-08-05 00:48:14
 */
@Service("menuPermService")
public class MenuPermServiceImpl extends ServiceImpl<MenuPermMapper, MenuPerm> implements MenuPermService {

}


