package com.lnn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lnn.entity.UserPerm;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户-权限(UserPerm)表数据库访问层
 *
 * @author makejava
 * @since 2024-08-04 19:40:56
 */
@Mapper
public interface UserPermMapper extends BaseMapper<UserPerm> {

}

