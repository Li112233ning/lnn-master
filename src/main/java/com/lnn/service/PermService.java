package com.lnn.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lnn.entity.Menu;
import com.lnn.entity.Perm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限(Perm)表服务接口
 *
 * @author makejava
 * @since 2024-08-04 19:45:32
 */
public interface PermService extends IService<Perm> {

    List<Perm> getSelectedPermsByMenuId(Long menuId);

    List<Perm> getSelectedPermsByUserId(Long userId);

    IPage<Perm> listPage(IPage<Menu> page, @Param(Constants.WRAPPER) Wrapper<Perm> queryWrapper);

}


