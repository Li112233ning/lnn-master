package com.lnn.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnn.entity.FlowLeave;
import com.lnn.mapper.FlowLeaveMapper;
import com.lnn.service.FlowLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 请假流程(FlowLeave)表服务实现类
 *
 * @author makejava
 * @since 2024-08-10 23:36:34
 */
@Service("flowLeaveService")
public class FlowLeaveServiceImpl extends ServiceImpl<FlowLeaveMapper, FlowLeave> implements FlowLeaveService {

    @Autowired
    FlowLeaveMapper leaveMapper;

    @Override
    public IPage<FlowLeave> listPage(IPage<FlowLeave> page, Wrapper<FlowLeave> queryWrapper) {
        return leaveMapper.listPage(page,queryWrapper);
    }

}


