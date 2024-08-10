package com.lnn.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.lnn.entity.FlowLeave;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 请假流程(FlowLeave)表数据库访问层
 *
 * @author makejava
 * @since 2024-08-10 23:36:35
 */
@Mapper
public interface FlowLeaveMapper extends BaseMapper<FlowLeave> {
    IPage<FlowLeave> listPage(IPage<FlowLeave> page, @Param(Constants.WRAPPER) Wrapper<FlowLeave> queryWrapper);

}

