package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import .mapper.FlowTaskMapper;
import .entity.FlowTask;
import .service.FlowTaskService;
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


