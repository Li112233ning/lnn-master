package entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 流程任务(FlowTask)表实体类
 *
 * @author makejava
 * @since 2024-08-10 23:36:50
 */
@TableName("redpig_flow_task")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowTask implements Serializable {
    //主键id
    private Long id;
    //删除状态：0、已删除 1、未删除
    private Integer deletestatus;
    //创建时间
    private Date createtime;
    //更新时间
    private Date updatetime;
    //任务ID
    private String taskid;
    //任务名称
    private String taskname;
    //执行ID
    private String taskexecutionid;
    //任务描述
    private String taskdescription;
    //任务处理人
    private String taskassignee;
    //流程实例ID
    private String taskprocessinstanceid;
    //创建者
    private String createBy;
    //更新者
    private String updateBy;

}

