package com.lnn.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lnn.entity.FlowLeave;
import org.apache.ibatis.annotations.Param;

/**
 * 请假流程(FlowLeave)表服务接口
 *
 * @author makejava
 * @since 2024-08-10 23:36:34
 */
public interface FlowLeaveService extends IService<FlowLeave> {
    IPage<FlowLeave> listPage(IPage<FlowLeave> page, @Param(Constants.WRAPPER) Wrapper<FlowLeave> queryWrapper);

}


