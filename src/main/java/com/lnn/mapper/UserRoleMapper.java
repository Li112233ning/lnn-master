package com.lnn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lnn.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户-角色(UserRole)表数据库访问层
 *
 * @author makejava
 * @since 2024-08-04 19:36:15
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}

