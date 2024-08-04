package com.lnn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lnn.entity.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 部门(dasd)表数据库访问层
 *
 * @author makejava
 * @since 2024-08-04 23:42:08
 */
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

}

