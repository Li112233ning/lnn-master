package com.lnn.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.lnn.entity.Menu;
import com.lnn.entity.Perm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface PermMapper extends BaseMapper<Perm> {

    List<Perm> getSelectedPermsByMenuId(@Param(value = "menuId")Long menuId);

    List<Perm> getPermsByUserId(@Param(value = "userId")Long userId);

    List<Perm> getSelectedPermsByUserId(@Param(value = "userId")Long userId);

    IPage<Perm> listPage(IPage<Menu> page, @Param(Constants.WRAPPER) Wrapper<Perm> queryWrapper);

}
