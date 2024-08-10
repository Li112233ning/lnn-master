package mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import .entity.FlowTask;
import org.springframework.stereotype.Repository;

/**
 * 流程任务(FlowTask)表数据库访问层
 *
 * @author makejava
 * @since 2024-08-10 23:36:50
 */
@Repository
public interface FlowTaskMapper extends BaseMapper<FlowTask> {

}

