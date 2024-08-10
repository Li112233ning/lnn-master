package com.lnn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnn.entity.FlowTask;
import com.lnn.mapper.FlowTaskMapper;
import com.lnn.service.FlowTaskService;
import org.springframework.stereotype.Service;

/**
 * 流程任务(FlowTask)表服务实现类
 *
 * @author makejava
 * @since 2024-08-10 23:36:50
 */
@Service("flowTaskService")
public class FlowTaskServiceImpl extends ServiceImpl<FlowTaskMapper, FlowTask> implements FlowTaskService {

}


