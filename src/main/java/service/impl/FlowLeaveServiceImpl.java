package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import .mapper.FlowLeaveMapper;
import .entity.FlowLeave;
import .service.FlowLeaveService;
import org.springframework.stereotype.Service;

/**
 * 请假流程(FlowLeave)表服务实现类
 *
 * @author makejava
 * @since 2024-08-10 23:36:34
 */
@Service("flowLeaveService")
public class FlowLeaveServiceImpl extends ServiceImpl<FlowLeaveMapper, FlowLeave> implements FlowLeaveService {

}


