package com.lnn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lnn.entity.FlowTask;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 流程任务(FlowTask)表数据库访问层
 *
 * @author makejava
 * @since 2024-08-10 23:36:50
 */
@Mapper
public interface FlowTaskMapper extends BaseMapper<FlowTask> {

}

