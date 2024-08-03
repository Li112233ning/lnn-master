package com.lnn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lnn.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getSelectedRolesByMenuId(@Param(value = "menuId")Long menuId);

    List<Role> getRolesByUserId(@Param(value = "userId")Long userId);

    List<Role> getSelectedRolesByUserId(@Param(value = "userId")Long userId);

}
