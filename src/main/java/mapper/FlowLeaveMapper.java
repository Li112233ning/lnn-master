package mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import .entity.FlowLeave;
import org.springframework.stereotype.Repository;

/**
 * 请假流程(FlowLeave)表数据库访问层
 *
 * @author makejava
 * @since 2024-08-10 23:36:35
 */
@Repository
public interface FlowLeaveMapper extends BaseMapper<FlowLeave> {

}

